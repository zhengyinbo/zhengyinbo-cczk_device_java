package com.bo.shirodemo.service;

import com.bo.shirodemo.entity.RolePermission;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface RolePermissionService {

    List<RolePermission> findByRoleId(Long roleId);

}
