package com.bo.shirodemo.service;

import com.bo.shirodemo.dto.DeviceDto;
import com.bo.shirodemo.entity.Device;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.vo.DeviceVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Description: 机器接口
 * @Author yb zheng
 * @Date 2025/7/11 09:23
 * @Version 1.0
 */

public interface DeviceService {

    Result<?> createDevice(DeviceVo vo);

    Device findByDeviceId(Long deviceId);

    Page<DeviceDto> queryDeviceList(Integer page, Integer limit, DeviceVo vo);

    List<Device> findByDeviceTypeId(Long deviceTypeId);

    Result<?> updateDevice(DeviceVo vo);

    Result<?> deleteDevice(DeviceVo vo);

    Result<?> bandDevice(DeviceVo vo);

    Result<?> unBandDevice(DeviceVo vo);

    Device findByDeviceNo(String deviceNo);


}
