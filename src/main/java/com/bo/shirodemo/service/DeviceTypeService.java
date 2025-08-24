package com.bo.shirodemo.service;

import com.bo.shirodemo.entity.DeviceType;
import com.bo.shirodemo.utils.Result;

/**
 * @Description: 机器类型接口
 * @Author yb zheng
 * @Date 2025/7/11 09:11
 * @Version 1.0
 */

public interface DeviceTypeService {

    Result<?> createDeviceType(String deviceTypeName, String remark);

    Result<?> queryDeviceTypeList(Integer page, Integer limit, String deviceTypeName);

    DeviceType findByDeviceId(Long deviceTypeId);

    Result<?> updateDeviceType(Long deviceTypeId, String deviceTypeName, String remark);

    Result<?> deleteDeviceType(Long deviceTypeId);

}
