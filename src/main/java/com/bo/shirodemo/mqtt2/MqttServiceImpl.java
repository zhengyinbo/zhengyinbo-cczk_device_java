package com.bo.shirodemo.mqtt2;

import com.bo.shirodemo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: Mqtt 接口实现类
 * @Author yb zheng
 * @Date 2025/7/16 11:24
 * @Version 1.0
 */

@Slf4j
@Service
public class MqttServiceImpl implements MqttService {

    private final MqttClientManager clientManager;
    private final MqttClientProperties clientProperties;

    public MqttServiceImpl(MqttClientManager clientManager, MqttClientProperties clientProperties) {
        this.clientManager = clientManager;
        this.clientProperties = clientProperties;
    }

    /**
     * 设置机器信息
     *
     * @param payload 消息体
     * @return Result
     */
    @Override
    public Result<?> setupDevice(MqttPayload payload) {
        String topic = MqttConstant.DEVICE_TOPIC;
        clientProperties.getClients().forEach((clientName, v) -> {
            try {
                clientManager.publish(clientName, topic, payload, 1, true);
            } catch (Exception e) {
                log.error("MqttServiceImpl-settingDevice error = ", e);
            }
        });
        return null;
    }
}
