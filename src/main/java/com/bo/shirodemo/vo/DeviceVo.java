package com.bo.shirodemo.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 机器实体
 * @Author yb zheng
 * @Date 2025/7/11 09:06
 * @Version 1.0
 */

@Data
public class DeviceVo {

    public Long deviceId;

    private String userId;

    private String userName;

    @NotBlank(message = "机器类型不能为空")
    private Long deviceTypeId;

    private String deviceTypeName;

    private String macAdd;

    @NotBlank(message = "设备编号不能为空")
    private String deviceNo;

    private String onlineState;

    private String remark;

    private Date createTime;

    private Date updateTime;

}
