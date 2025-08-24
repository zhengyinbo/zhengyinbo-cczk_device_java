package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.entity.Permission;
import com.bo.shirodemo.repository.PermissionRepository;
import com.bo.shirodemo.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Override
    public Permission findByPermissionId(Long permissionId) {
        return repository.findByPermissionId(permissionId);
    }
}
