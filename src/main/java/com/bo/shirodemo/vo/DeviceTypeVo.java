package com.bo.shirodemo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 机器类型实体
 * @Author yb zheng
 * @Date 2025/7/11 09:09
 * @Version 1.0
 */

@Data
public class DeviceTypeVo {

    private Long deviceTypeId;

    private String deviceTypeName;

    private String remark;

    // 是否已删除 0：否，1：是
    private String isDelete;

    private Date createTime;

    private Date updateTime;

}
