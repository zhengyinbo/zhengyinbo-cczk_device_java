package com.bo.shirodemo.repository;

import com.bo.shirodemo.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRoleId(Long roleId);

}
