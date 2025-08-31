package com.bo.shirodemo.controller;

import com.bo.shirodemo.dto.DeviceDto;
import com.bo.shirodemo.service.DeviceService;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import com.bo.shirodemo.vo.DeviceVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 机器管理
 * @Author yb zheng
 * @Date 2025/7/11 08:59
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

//    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/create")
    public Result<?> createDevice(@RequestBody DeviceVo deviceVo) {
        try {
            return deviceService.createDevice(deviceVo);
        } catch (Exception e) {
            log.info("DeviceController-createDevice error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

//    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping("/list")
    public Result<?> queryDeviceList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                   DeviceVo deviceVo) {
        try {
            Page<DeviceDto> deviceDtos = deviceService.queryDeviceList(page, limit, deviceVo);
            return ReturnResult.success(deviceDtos.getContent(), deviceDtos.getTotalElements());
        } catch (Exception e) {
            log.info("DeviceController-queryDeviceList error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

//    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/update")
    public Result<?> updateDevice(@RequestBody DeviceVo deviceVo) {
        try {
            return deviceService.updateDevice(deviceVo);
        } catch (Exception e) {
            log.info("DeviceController-updateDevice error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

//    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/delete")
    public Result<?> deleteDevice(@RequestBody DeviceVo deviceVo) {
        try {
            return deviceService.deleteDevice(deviceVo);
        } catch (Exception e) {
            log.info("DeviceController-deleteDevice error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/band")
    public Result<?> bandDevice(@RequestBody DeviceVo deviceVo) {
        try {
            return deviceService.bandDevice(deviceVo);
        } catch (Exception e) {
            log.info("DeviceController-bandUser error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping("/unBand")
    public Result<?> unBandDevice(@RequestBody DeviceVo deviceVo) {
        try {
            return deviceService.unBandDevice(deviceVo);
        } catch (Exception e) {
            log.info("DeviceController-unBandDevice error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }
}
