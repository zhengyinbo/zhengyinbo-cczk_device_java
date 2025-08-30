package com.bo.shirodemo.repository;

import com.bo.shirodemo.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long>, JpaSpecificationExecutor<DeviceType> {

    DeviceType findByDeviceTypeName(String deviceTypeName);

}
