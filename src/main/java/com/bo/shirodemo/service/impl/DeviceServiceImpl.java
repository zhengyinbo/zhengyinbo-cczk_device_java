package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.dto.DeviceDto;
import com.bo.shirodemo.dto.DeviceTypeDto;
import com.bo.shirodemo.dto.UserDto;
import com.bo.shirodemo.entity.Device;
import com.bo.shirodemo.entity.DeviceType;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.mqtt2.MqttClientManager;
import com.bo.shirodemo.mqtt2.MqttClientProperties;
import com.bo.shirodemo.mqtt2.MqttConstant;
import com.bo.shirodemo.repository.DeviceRepository;
import com.bo.shirodemo.service.DeviceService;
import com.bo.shirodemo.service.DeviceTypeService;
import com.bo.shirodemo.service.UserService;
import com.bo.shirodemo.utils.Constant;
import com.bo.shirodemo.utils.Ognl;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import com.bo.shirodemo.vo.DeviceVo;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForBigDecimal;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 机器接口实现类
 * @Author yb zheng
 * @Date 2025/7/11 09:37
 * @Version 1.0
 */

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;
    private final UserService userService;
    private final DeviceTypeService deviceTypeService;

    public DeviceServiceImpl(DeviceRepository repository, UserService userService, DeviceTypeService deviceTypeService) {
        this.repository = repository;
        this.userService = userService;
        this.deviceTypeService = deviceTypeService;
    }

    @Override
    public Result<?> createDevice(DeviceVo vo) {
        Device byDeviceNo = this.findByDeviceNo(vo.getDeviceNo());
        if (Ognl.isNotEmpty(byDeviceNo)) {
            return ReturnResult.fail(400, "添加失败，机器编号已存在");
        }
        User user = userService.getUserInfo();
        Device device = new Device();
        device.setDeviceNo(vo.getDeviceNo());
        device.setDeviceTypeId(vo.getDeviceTypeId());
        device.setUserId(user.getUserId());
        device.setMacAdd(vo.getMacAdd());
        device.setRemark(vo.getRemark());
        device.setIsDelete("0");
        device.setCreateTime(new Date());
        device.setUpdateTime(new Date());
        repository.save(device);
        return ReturnResult.success("添加成功");
    }

    @Override
    public Device findByDeviceId(Long deviceId) {
        return repository.findById(deviceId).orElse(null);
    }

    @Override
    public Page<DeviceDto> queryDeviceList(Integer page, Integer limit, DeviceVo vo) {
        User user = userService.getUserInfo();
        Specification<Device> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("isDelete"), "0"));
            // 非管理员只能查看自己的机器
            if (!Constant.USER_TYPE_ADMIN.equals(user.getUserType())) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), user.getUserId()));
            }
            if (Ognl.isNotEmpty(vo.getDeviceNo())) {
                predicates.add(criteriaBuilder.equal(root.get("deviceNo"), vo.getDeviceNo()));
            }
            if (Ognl.isNotEmpty(vo.getUserId())) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), vo.getUserId()));
            }
            if (Ognl.isNotEmpty(vo.getDeviceTypeId())) {
                predicates.add(criteriaBuilder.equal(root.get("deviceTypeId"), vo.getDeviceTypeId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(page-1, limit, Sort.Direction.DESC, "deviceId");
        Page<Device> all = repository.findAll(specification, pageable);
        Map<Long, String> typeMap =
                deviceTypeService.findAll().stream().collect(Collectors.toMap(DeviceTypeDto::getDeviceTypeId, DeviceTypeDto::getDeviceTypeName));
        Map<Long, String> userMap =
                userService.findAll().stream().collect(Collectors.toMap(UserDto::getUserId, UserDto::getUserName));
        List<DeviceDto> deviceDtoList = new ArrayList<>();
        for (Device device : all.getContent()) {
            DeviceDto dto = new DeviceDto();
            BeanUtils.copyProperties(device, dto);
            dto.setUserName(userMap.get(device.getUserId()));
            dto.setDeviceTypeName(typeMap.get(device.getDeviceTypeId()));
            deviceDtoList.add(dto);
        }
        return new PageImpl<>(deviceDtoList, pageable, all.getTotalElements());
    }

    @Override
    public List<Device> findByDeviceTypeId(Long deviceTypeId) {
        return repository.findByDeviceTypeId(deviceTypeId);
    }

    @Override
    public Result<?> updateDevice(DeviceVo vo) {
        Optional<Device> byId = repository.findById(vo.getDeviceId());
        if (byId.isEmpty()) {
            return ReturnResult.fail(400, "修改失败，请选择机器");
        }
        Device device = byId.get();
        if (Ognl.isNotEmpty(vo.getDeviceTypeId())) {
            device.setDeviceTypeId(vo.getDeviceTypeId());
        }
        device.setMacAdd(vo.getMacAdd());
        device.setRemark(vo.getRemark());
        repository.save(device);
        return ReturnResult.success("修改成功");
    }

    @Override
    public Result<?> deleteDevice(DeviceVo vo) {
        Optional<Device> byId = repository.findById(vo.getDeviceId());
        if (byId.isEmpty()) {
            return ReturnResult.fail(400, "删除失败，请选择机器");
        }
        Device device = byId.get();
        device.setIsDelete("1");
        repository.save(device);
        return ReturnResult.success("删除成功");
    }

    @Override
    public Result<?> bandDevice(DeviceVo vo) {
        Optional<Device> byId = repository.findById(vo.getDeviceId());
        if (byId.isEmpty()) {
            return ReturnResult.fail(400, "绑定失败，请选择机器");
        }
        Device device = byId.get();
        if (Ognl.isNotEmpty(device.getUserId()) && !device.getUserId().equals(1L)) {
            return ReturnResult.fail(400, "绑定失败，改机器已绑定其他用户");
        }
        device.setUserId(vo.getUserId());
        repository.save(device);
        return ReturnResult.success("绑定成功");
    }

    @Override
    public Result<?> unBandDevice(DeviceVo vo) {
        Optional<Device> byId = repository.findById(vo.getDeviceId());
        if (byId.isEmpty()) {
            return ReturnResult.fail(400, "解绑失败，请选择机器");
        }
        Device device = byId.get();
        device.setUserId(1L);
        repository.save(device);
        return ReturnResult.success("解绑成功");
    }

    @Override
    public Device findByDeviceNo(String deviceNo) {
        return repository.findByDeviceNo(deviceNo);
    }

}
