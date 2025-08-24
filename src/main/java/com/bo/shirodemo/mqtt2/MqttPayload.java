package com.bo.shirodemo.mqtt2;

import lombok.Data;

/**
 * @Description: Mqtt 消息体
 * @Author yb zheng
 * @Date 2025/7/16 11:24
 * @Version 1.0
 */

@Data
public class MqttPayload {

    private String id;

    // 主题
    private String topic;

    // 消息
    private String message;

    // 机器编号
    private String deviceNo;

    // 订单金额
    private String amount;

}
