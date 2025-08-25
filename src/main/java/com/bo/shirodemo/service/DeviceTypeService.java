package com.bo.shirodemo.service;

import com.bo.shirodemo.dto.DeviceTypeDto;
import com.bo.shirodemo.entity.DeviceType;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.vo.DeviceTypeVo;
import org.springframework.data.domain.Page;

/**
 * @Description: 机器类型接口
 * @Author yb zheng
 * @Date 2025/7/11 09:11
 * @Version 1.0
 */

public interface DeviceTypeService {

    Result<?> createDeviceType(String deviceTypeName, String remark);

    Page<DeviceTypeDto> queryDeviceTypeList(Integer page, Integer limit, String deviceTypeName);

    DeviceType findByDeviceId(Long deviceTypeId);

    Result<?> updateDeviceType(DeviceTypeVo vo);

    Result<?> deleteDeviceType(Long deviceTypeId);

}
