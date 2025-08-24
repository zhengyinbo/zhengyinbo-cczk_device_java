package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.entity.Role;
import com.bo.shirodemo.repository.RoleRepository;
import com.bo.shirodemo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role findByRoleId(Long roleId) {
        return repository.findByRoleId(roleId);
    }
}
