package com.bo.shirodemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * @Description: 机器实体
 * @Author yb zheng
 * @Date 2025/7/11 09:06
 * @Version 1.0
 */

@Data
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long deviceId;

    private Long userId;

    private Long deviceTypeId;

    private String macAdd;

    private String deviceNo;

    private String onlineState;

    private String remark;

    // 是否已删除 0：否，1：是
    private String isDelete;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

}
