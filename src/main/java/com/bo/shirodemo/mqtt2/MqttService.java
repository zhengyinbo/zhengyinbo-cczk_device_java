package com.bo.shirodemo.mqtt2;

import com.bo.shirodemo.utils.Result;

/**
 * @Description: Mqtt 接口
 * @Author yb zheng
 * @Date 2025/7/16 11:21
 * @Version 1.0
 */

public interface MqttService {

    Result<?> setupDevice(MqttPayload payload);

}
