package com.bo.shirodemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 订单实体
 * @Author yb zheng
 * @Date 2025/7/11 15:03
 * @Version 1.0
 */

@Data
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNo;

    @NotBlank(message = "用户id不能为空")
    private Long userId;

    private String userName;

    private Long deviceTypeId;

    private String deviceTypeName;

    private Long deviceId;

    @NotBlank(message = "机器编号不能为空")
    private String deviceNo;

    // 投币数
    private BigDecimal amount;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

}
