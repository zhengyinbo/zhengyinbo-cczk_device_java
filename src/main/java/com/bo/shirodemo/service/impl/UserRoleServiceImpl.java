package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.entity.UserRole;
import com.bo.shirodemo.repository.UserRoleRepository;
import com.bo.shirodemo.service.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository repository;

    public UserRoleServiceImpl(UserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
