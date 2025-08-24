package com.bo.shirodemo.repository;

import com.bo.shirodemo.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByPermissionId(Long permissionId);

}
