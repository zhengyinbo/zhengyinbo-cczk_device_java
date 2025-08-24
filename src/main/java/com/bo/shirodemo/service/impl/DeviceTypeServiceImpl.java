package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.entity.Device;
import com.bo.shirodemo.entity.DeviceType;
import com.bo.shirodemo.repository.DeviceTypeRepository;
import com.bo.shirodemo.service.DeviceService;
import com.bo.shirodemo.service.DeviceTypeService;
import com.bo.shirodemo.utils.Ognl;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 机器接口实现类
 * @Author yb zheng
 * @Date 2025/7/11 09:14
 * @Version 1.0
 */

@Slf4j
@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {

    private final DeviceTypeRepository repository;
    private final DeviceService deviceService;

    public DeviceTypeServiceImpl(DeviceTypeRepository repository, DeviceService deviceService) {
        this.repository = repository;
        this.deviceService = deviceService;
    }

    @Override
    public Result<?> createDeviceType(String deviceTypeName, String remark) {
        if (Ognl.isEmpty(deviceTypeName)) {
            return ReturnResult.fail(400, "添加失败，请输入机器类型名称");
        }
        DeviceType type = new DeviceType();
        type.setDeviceTypeName(deviceTypeName);
        type.setRemark(remark);
        type.setIsDelete("0");
        repository.save(type);
        return ReturnResult.success("添加成功");
    }

    @Override
    public Result<?> queryDeviceTypeList(Integer page, Integer limit, String deviceTypeName) {
        return null;
    }

    @Override
    public DeviceType findByDeviceId(Long deviceTypeId) {
        return repository.findById(deviceTypeId).orElse(null);
    }

    @Override
    public Result<?> updateDeviceType(Long deviceTypeId, String deviceTypeName, String remark) {
        if (Ognl.isEmpty(deviceTypeName)) {
            return ReturnResult.fail(400, "修改失败，请输入机器类型名称");
        }
        Optional<DeviceType> byId = repository.findById(deviceTypeId);
        if (byId.isEmpty()) {
            return ReturnResult.fail(400, "修改失败，请选择机器类型");
        }
        DeviceType type = byId.get();
        type.setDeviceTypeName(deviceTypeName);
        type.setRemark(remark);
        repository.save(type);
        return ReturnResult.success("修改成功");
    }

    @Override
    public Result<?> deleteDeviceType(Long deviceTypeId) {
        List<Device> byDeviceTypeId = deviceService.findByDeviceTypeId(deviceTypeId);
        if (Ognl.isNotEmpty(byDeviceTypeId)) {
            return ReturnResult.fail(400, "删除失败，该机器类型下存在机器，无法删除");
        }
        Optional<DeviceType> byId = repository.findById(deviceTypeId);
        if (byId.isEmpty()) {
            return ReturnResult.fail(400, "修改失败，请选择机器类型");
        }
        DeviceType type = byId.get();
        type.setIsDelete("1");
        repository.save(type);
        return ReturnResult.success("删除成功");
    }
}
