package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.entity.RolePermission;
import com.bo.shirodemo.repository.RolePermissionRepository;
import com.bo.shirodemo.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

@Service
@Slf4j
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionRepository repository;

    @Override
    public List<RolePermission> findByRoleId(Long roleId) {
        return repository.findByRoleId(roleId);
    }
}
