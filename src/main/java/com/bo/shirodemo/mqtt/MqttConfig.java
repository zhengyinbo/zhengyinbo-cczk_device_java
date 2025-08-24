package com.bo.shirodemo.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
public class MqttConfig {

    @Value("${mqtt.broker-url}")
    private String brokerUrl;
    @Value("${mqtt.client-id}")
    private String clientId;
    @Value("${mqtt.username}")
    private String username;
    @Value("${mqtt.password}")
    private String password;
    @Value("${mqtt.topics.heartbeat}")
    private String heartbeatTopic;
    @Value("${mqtt.topics.order}")
    private String orderTopic;

    private final MqttMessageHandler handler;

    public MqttConfig(MqttMessageHandler handler) {
        this.handler = handler;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
        MqttConnectOptions opts = new MqttConnectOptions();
        opts.setUserName(username);
        opts.setPassword(password.toCharArray());
        opts.setCleanSession(true);
        client.connect(opts);

        // 订阅
        client.subscribe(heartbeatTopic, handler::handleHeartbeat);
        log.info("心跳订阅成功！");
        client.subscribe(orderTopic, handler::handleOrder);
        log.info("订单订阅成功！");
        return client;
    }
}
