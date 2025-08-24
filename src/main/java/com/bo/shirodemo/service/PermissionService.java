package com.bo.shirodemo.service;

import com.bo.shirodemo.entity.Permission;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface PermissionService {

    Permission findByPermissionId(Long permissionId);

}
