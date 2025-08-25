package com.bo.shirodemo.controller;

import com.bo.shirodemo.dto.UserDto;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.service.UserService;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import com.bo.shirodemo.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户控制类
 * @Author yb zheng
 * @Date 2025/7/9 09:58
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @RequiresRoles("admin")
    @RequestMapping("/create")
    public Result<?> createUser(@RequestBody User user) {
        try {
            return userService.saveUser(user);
        } catch (Exception e) {
            log.info("UserController-createUser error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

    @RequiresRoles("admin")
    @RequestMapping("/update")
    public Result<?> updateUser(@RequestBody User user) {
        try {
            return userService.updateUser(user);
        } catch (Exception e) {
            log.info("UserController-updateUser error = ", e);
            return ReturnResult.fail(500, e.getMessage());
        }
    }

//    @RequiresRoles("admin")
    @RequestMapping("/list")
    public Result<?> queryUserList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                   UserVo userVo) {
        Page<UserDto> userDtos = userService.queryUserList(page, limit, userVo);
        return ReturnResult.success(userDtos.getContent(), userDtos.getTotalElements());
    }

}
