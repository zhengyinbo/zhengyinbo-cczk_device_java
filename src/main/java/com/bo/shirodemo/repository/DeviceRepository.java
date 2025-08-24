package com.bo.shirodemo.repository;

import com.bo.shirodemo.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

public interface DeviceRepository extends JpaRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    List<Device> findByDeviceTypeId(Long deviceTypeId);

    Device findByDeviceNo(String deviceNo);

}
