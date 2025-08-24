package com.bo.shirodemo.mqtt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
//@Component
public class MqttMessageHandler {

    private final RedisService redisService;

    private final DeviceOrderRepository orderRepo;

    private final ObjectMapper om = new ObjectMapper();

    public MqttMessageHandler(RedisService redisService, DeviceOrderRepository orderRepo) {
        this.redisService = redisService;
        this.orderRepo = orderRepo;
    }

    public void handleHeartbeat(String topic, MqttMessage msg) {
        try {
            String payload = new String(msg.getPayload(), StandardCharsets.UTF_8);
            JsonNode root = om.readTree(payload);
            String deviceId = root.get("deviceId").asText();
            redisService.updateHeartbeat(Long.valueOf(deviceId));
            log.info("topic: {}, {} 已心跳", topic, deviceId);
        } catch(Exception e) {
            log.error("MqttMessageHandler-handleHeartbeat error = ", e);
        }
    }

    public void handleOrder(String topic, MqttMessage msg) {
        try {
            String payload = new String(msg.getPayload(), StandardCharsets.UTF_8);
            DeviceOrder order = om.readValue(payload, DeviceOrder.class);
            order.setTimestamp(Instant.now());
            orderRepo.save(order);
            log.info("topic: {}, 订单保存: {}", topic, order.getOrderId());
        } catch (Exception e) {
            log.error("MqttMessageHandler-handleOrder error = ", e);
        }
    }
}
