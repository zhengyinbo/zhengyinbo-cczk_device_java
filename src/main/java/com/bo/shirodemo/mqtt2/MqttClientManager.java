package com.bo.shirodemo.mqtt2;

import com.bo.shirodemo.entity.Device;
import com.bo.shirodemo.mqtt.RedisService;
import com.bo.shirodemo.service.DeviceService;
import com.bo.shirodemo.service.OrdersService;
import com.bo.shirodemo.utils.Constant;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.vo.OrdersVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: Mqtt管理类
 * @Author yb zheng
 * @Date 2025/7/16 09:41
 * @Version 1.0
 */

@Slf4j
@Component
public class MqttClientManager {

    // 存储所有客户端实例
    private final Map<String, MqttClient> clients = new ConcurrentHashMap<>();

    private final MqttClientProperties mqttProperties;
    private final RedisService redisService;
    private final DeviceService deviceService;
    private final OrdersService ordersService;

    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    public MqttClientManager(MqttClientProperties mqttProperties,
                             RedisService redisService, DeviceService deviceService, OrdersService ordersService) {
        this.mqttProperties = mqttProperties;
        this.redisService = redisService;
        this.deviceService = deviceService;
        this.ordersService = ordersService;
    }

    // 应用启动完成后初始化MQTT客户端
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        initializeClientsWithRetry(mqttProperties.getMaxRetries());
    }

    private void initializeClientsWithRetry(int maxRetries) {
        mqttProperties.getClients().forEach((name, config) -> {
            int attempt = 0;
            while (attempt < maxRetries) {
                try {
                    MqttClient client = new MqttClient(
                            config.getBrokerUrl(),
                            config.getClientId(),
                            new MemoryPersistence()
                    );
                    // 客户端连接配置
                    MqttConnectOptions options = getMqttConnectOptions(config);
                    // 设置客户端回调逻辑
                    setupClientCallback(client, name, config.getTopics());
                    client.connect(options);
                    clients.put(name, client);
                    break; // 成功退出循环
                } catch (MqttException e) {
                    handleInitializationFailure(name, ++attempt, maxRetries, e);
                }
            }
        });
    }

    /**
     * 处理初始化失败的逻辑，包括重试机制
     */
    private void handleInitializationFailure(String clientName, int attempt, int maxRetries, Exception e) {
        log.error("初始化 MQTT 客户端 [{}] 失败，尝试次数：{}", clientName, attempt, e);
        if (attempt >= maxRetries) {
            log.error("MQTT 客户端 [{}] 达到最大重试次数 {}，初始化失败。", clientName, maxRetries);
        } else {
            int delay = Math.min(30, (int) Math.pow(2, attempt)); // 最大延迟30秒
            log.warn("将在 {} 秒后重试 MQTT 客户端 [{}] 初始化...", delay, clientName);

            try {
                Thread.sleep(delay * 1000L);
            } catch (InterruptedException ie) {
                log.warn("等待重试时被中断", ie);
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
        }
    }

    private static MqttConnectOptions getMqttConnectOptions(MqttClientProperties.ClientConfig config) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(config.getUsername());
        options.setPassword(config.getPassword().toCharArray());
        options.setAutomaticReconnect(true); // 启用自动重连
        options.setCleanSession(false); // 设置为false以保留会话状态
        return options;
    }

    /**
     * 设置客户端回调逻辑
     */
    private void setupClientCallback(MqttClient client, String clientName, List<MqttClientProperties.TopicConfig> topics) {
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                log.info("MQTT 连接成功: {}", serverURI);
                if (reconnect) {
                    log.info("MQTT 客户端[{}]断开后重连成功", clientName);
                } else {
                    log.info("首次连接到 Broker: {}", serverURI);
                }
                subscribeTopics(client, clientName, topics);
            }

            @Override
            public void connectionLost(Throwable cause) {
                log.error("MQTT 连接丢失: {}", cause.getMessage(), cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                handleMessage(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.debug("消息发送完成");
            }
        });
    }

    private void subscribeTopics(MqttClient client, String name, List<MqttClientProperties.TopicConfig> topics) {
        for (MqttClientProperties.TopicConfig topic : topics) {
            try {
                client.subscribe(topic.getTopic(), topic.getQos());
                log.info("客户端 [{}] 已成功订阅主题: {}", name, topic.getTopic());
            } catch (MqttException e) {
                log.error("客户端 [{}] 订阅主题 [{}] 失败", name, topic.getTopic(), e);
            }
        }
    }

    private void handleMessage(String topic, MqttMessage message) {
        try {
            String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
            JsonNode root = om.readTree(payload);
            String deviceNo = root.get("deviceNo").asText();
            String amount = root.get("amount").asText();
            Device device = deviceService.findByDeviceNo(deviceNo);

            log.info("收到消息 - 主题: {}, 内容: {}", topic, payload);

            // todo 业务处理
            switch (topic) {
                // 心跳包
                case "device/heartbeat" -> {
                    redisService.updateHeartbeat(device.getDeviceId());
                }
                // 订单包
                case "device/order" -> {
                    OrdersVo ordersVo = new OrdersVo();
                    ordersVo.setDeviceNo(deviceNo);
                    ordersVo.setAmount(new BigDecimal(amount));
                    Result<?> orders = ordersService.createOrders(ordersVo);
                    if (Constant.SUCCESS_CODE == orders.getCode()) {
                        log.info("Mqtt - 订单保存成功");
                    } else {
                        log.info("Mqtt - 订单保存失败");
                    }
                }
            }
        } catch (Exception e) {
            log.error("解析 MQTT 消息出错: {}", e.getMessage(), e);
        }
    }

    /**
     * 向指定客户端和主题发布消息
     *
     * @param clientName 客户端名称（配置中定义）
     * @param topic      主题名称
     * @param payload    要发布的对象内容（将自动转换为 JSON）
     * @param qos        消息质量等级（0, 1, 2）
     * @param retained   是否保留消息
     */
    public void publish(String clientName, String topic, Object payload, int qos, boolean retained) {
        MqttClient client = clients.get(clientName);
        if (client != null && client.isConnected()) {
            try {
                // 使用 objectMapper 将对象转为 JSON 字符串，并编码为字节数组
                String jsonPayload = om.writeValueAsString(payload);
                client.publish(topic, jsonPayload.getBytes(StandardCharsets.UTF_8), qos, retained);
                log.info("消息已发布到主题: {}, 内容: {}", topic, jsonPayload);
            } catch (JsonProcessingException e) {
                log.error("MQTT 消息序列化失败", e);
            } catch (MqttException e) {
                log.error("发布 MQTT 消息失败: {}", e.getMessage(), e);
            }
        } else {
            log.warn("MQTT 客户端 [{}] 不在线，无法发布消息到主题 [{}]", clientName, topic);
        }
    }

    // ========== 健康检查与自动重连 ========== 60s
    @Scheduled(fixedRate = 60_000)
    public void checkConnections() {
        log.info("健康检查与自动重连");
        clients.forEach((name, client) -> {
            if (!client.isConnected()) {
                log.warn("MQTT 客户端 [{}] 当前未连接，尝试重新连接", name);
                try {
                    client.reconnect();
                } catch (MqttException e) {
                    log.error("手动重连失败", e);
                }
            }
        });
    }

    // ========== 资源释放 ==========
    @PreDestroy
    public void destroy() {
        clients.forEach((name, client) -> {
            try {
                client.disconnect();
                log.info("MQTT 客户端 [{}] 已断开连接", name);
            } catch (MqttException e) {
                log.warn("关闭 MQTT 客户端 [{}] 时发生错误", name, e);
            }
        });
    }
}