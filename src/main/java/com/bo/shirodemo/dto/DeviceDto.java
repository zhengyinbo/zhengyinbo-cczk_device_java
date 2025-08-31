package com.bo.shirodemo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 机器实体
 * @Author yb zheng
 * @Date 2025/7/11 09:06
 * @Version 1.0
 */

@Data
public class DeviceDto {

    public Long deviceId;

    private Long userId;

    private String userName;

    private Long deviceTypeId;

    private String deviceTypeName;

    private String macAdd;

    private String deviceNo;

    private String onlineState;

    private String remark;

    private Date createTime;

    private Date updateTime;

}
