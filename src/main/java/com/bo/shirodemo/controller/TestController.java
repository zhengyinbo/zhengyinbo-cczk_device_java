package com.bo.shirodemo.controller;


import com.bo.shirodemo.mqtt2.MqttClientManager;
import com.bo.shirodemo.mqtt2.MqttClientProperties;
import com.bo.shirodemo.mqtt2.MqttPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mqtt/test")
public class TestController {

    private final MqttClientManager clientManager;
    private final MqttClientProperties clientProperties;

    public TestController(MqttClientManager clientManager, MqttClientProperties clientProperties) {
        this.clientManager = clientManager;
        this.clientProperties = clientProperties;
    }

    @RequestMapping("/heartBreak")
    public void test(@RequestBody MqttPayload payload) {
        String topic = payload.getTopic();
        clientProperties.getClients().forEach((clientName, v) -> {
            try {
                clientManager.publish(clientName, topic, payload, 1, true);
            } catch (Exception e) {
                log.error("MqttServiceImpl-settingDevice error = ", e);
            }
        });
        log.info("TestController-test = {}", payload);
    }

}
