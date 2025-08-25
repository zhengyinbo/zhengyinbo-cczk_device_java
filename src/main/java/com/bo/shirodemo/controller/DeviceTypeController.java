package com.bo.shirodemo.controller;

import com.bo.shirodemo.dto.DeviceTypeDto;
import com.bo.shirodemo.service.DeviceTypeService;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import com.bo.shirodemo.vo.DeviceTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 设备类型管理
 * @Author yb zheng
 * @Date 2025/7/11 08:59
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/deviceType")
public class DeviceTypeController {

    private final DeviceTypeService deviceTypeService;

    public DeviceTypeController(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/create")
    public Result<?> createDeviceType(@RequestBody DeviceTypeVo deviceTypeVo) {
        try {
            return deviceTypeService.createDeviceType(deviceTypeVo.getDeviceTypeName(), deviceTypeVo.getRemark());
        } catch (Exception e) {
            log.info("deviceTypeController-createdeviceType error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

//    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping("/list")
    public Result<?> queryDeviceTypeList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                   DeviceTypeVo deviceTypeVo) {
        try {
            Page<DeviceTypeDto> deviceTypeDtos = deviceTypeService.queryDeviceTypeList(page, limit, deviceTypeVo.getDeviceTypeName());
            return ReturnResult.success(deviceTypeDtos.getContent(), deviceTypeDtos.getTotalElements());
        } catch (Exception e) {
            log.info("deviceTypeController-queryDeviceTypeList error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/update")
    public Result<?> updateDeviceType(@RequestBody DeviceTypeVo deviceTypeVo) {
        try {
            return deviceTypeService.updateDeviceType(deviceTypeVo);
        } catch (Exception e) {
            log.info("deviceTypeController-updateDeviceType error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/delete")
    public Result<?> deleteDeviceType(@RequestBody DeviceTypeVo deviceTypeVo) {
        try {
            return deviceTypeService.deleteDeviceType(deviceTypeVo.getDeviceTypeId());
        } catch (Exception e) {
            log.info("deviceTypeController-deletedeviceType error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }
}
