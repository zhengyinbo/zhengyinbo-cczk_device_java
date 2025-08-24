package com.bo.shirodemo.mqtt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceOrderRepository extends JpaRepository<DeviceOrder, Long> {
}
