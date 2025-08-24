package com.bo.shirodemo.controller;

import com.bo.shirodemo.mqtt2.MqttPayload;
import com.bo.shirodemo.mqtt2.MqttService;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: Mqtt 控制层
 * @Author yb zheng
 * @Date 2025/7/16 10:30
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttService mqttService;

    public MqttController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @RequestMapping("setup/device")
    public Result<?> setupDevice(@RequestBody MqttPayload payload) {
        try {
            return mqttService.setupDevice(payload);
        } catch (Exception e) {
            log.error("MqttController-setupDevice error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }
}
