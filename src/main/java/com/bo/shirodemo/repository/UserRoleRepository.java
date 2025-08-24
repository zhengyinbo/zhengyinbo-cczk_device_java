package com.bo.shirodemo.repository;

import com.bo.shirodemo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUserId(Long userId);

}
