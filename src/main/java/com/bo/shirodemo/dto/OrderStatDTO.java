package com.bo.shirodemo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 订单统计DTO
 * @Author yb zheng
 * @Date 2025/7/14 15:11
 * @Version 1.0
 */

@Data
public class OrderStatDTO {

    private String period;

    private Long orderCount;

    private BigDecimal totalAmount;

    public OrderStatDTO(String period, Long orderCount, BigDecimal totalAmount) {
        this.period = period;
        this.orderCount = orderCount;
        this.totalAmount = totalAmount;
    }

}
