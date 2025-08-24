package com.bo.shirodemo.service;

import com.bo.shirodemo.entity.UserRole;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface UserRoleService {

    List<UserRole> findByUserId(Long userId);

}
