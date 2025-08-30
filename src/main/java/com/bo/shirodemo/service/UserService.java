package com.bo.shirodemo.service;

import com.bo.shirodemo.dto.UserDto;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.vo.UserVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/


public interface UserService {

    Page<UserDto> queryUserList(Integer page, Integer limit, UserVo vo);

    User findByUserName(String userName);

    User findByUserId(Long userId);

    User getUserInfo();

    Result<?> saveUser(User user);

    Result<?> updateUser(User user);

    Result<?> bandUser(User user);

    List<UserDto> findAll();

}
