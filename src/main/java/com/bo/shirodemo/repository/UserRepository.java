package com.bo.shirodemo.repository;

import com.bo.shirodemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUserName(String userName);

}
