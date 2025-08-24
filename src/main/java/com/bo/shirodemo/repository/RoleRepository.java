package com.bo.shirodemo.repository;

import com.bo.shirodemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleId(Long roleId);

}
