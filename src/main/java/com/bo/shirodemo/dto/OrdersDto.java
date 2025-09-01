package com.bo.shirodemo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 订单dto
 * @Author yb zheng
 * @Date 2025/7/11 15:11
 * @Version 1.0
 */

@Data
public class OrdersDto {

    private Long id;

    private String orderNo;

    private Long userId;

    private String userName;

    private Long deviceTypeId;

    private String deviceTypeName;

    private Long deviceId;

    private String deviceNo;

    // 投币数
    private BigDecimal amount;

    private Date createTime;

    private Date updateTime;

}
