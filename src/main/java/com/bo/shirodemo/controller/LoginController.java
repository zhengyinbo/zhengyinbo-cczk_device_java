package com.bo.shirodemo.controller;

import com.bo.shirodemo.entity.Role;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.entity.UserRole;
import com.bo.shirodemo.service.RoleService;
import com.bo.shirodemo.service.UserRoleService;
import com.bo.shirodemo.service.UserService;
import com.bo.shirodemo.utils.LoginResult;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import com.bo.shirodemo.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 *
 * @Author bo
 * @DATE 2019/12/23
 **/

@Slf4j
@RestController
public class LoginController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    public LoginController(UserService userService, RoleService roleService, UserRoleService userRoleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @PostMapping(value = "login")
    public Object Login(@RequestBody UserVo userVo) {
        Subject subject = SecurityUtils.getSubject(); //主体提交认证
//        SecurityUtils.getSubject().getSession().setTimeout(6 * 60 * 60 * 1000); //设置session失效时间：-1000l表示无限时长，默认为1800000表示30分钟 30*60*1000
        SecurityUtils.getSubject().getSession().setTimeout(-1L); //设置session失效时间：-1000l表示无限时长，默认为1800000表示30分钟 30*60*1000
        UsernamePasswordToken token;

        User user = userService.findByUserName(userVo.getUserName());
        if (user != null && !"1".equals(user.getStatus())) {
//            throw new MyIllegalArgumentException(ExceptionEnum.USER_HAD_BEEN_STOP);
        }
        try {
            token = new UsernamePasswordToken(userVo.getUserName(), userVo.getPassword());
            subject.login(token);
            return LoginResult.success(subject.getSession().getId());//返回登录状态
        } catch (IncorrectCredentialsException e) {
            return LoginResult.fail(401, "密码错误");
        } catch (LockedAccountException e) {
            return LoginResult.fail(401, "登录失败，该用户已被冻结，请联系管理员");
        } catch (AuthenticationException e) {
            return LoginResult.fail(401, "该用户不存在");
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    //未登录
    @GetMapping(value = "/unauth")
    public Object unauth() {
        return LoginResult.failLogin(1001, "未登录");
    }

    //获取用户角色
    @GetMapping(value = "/role")
    public Result<?> getUserRole(){
        User user = userService.findByUserName(String.valueOf(SecurityUtils.getSubject().getPrincipals()));
        List<UserRole> userRoleList = userRoleService.findByUserId(user.getUserId());
        List<String> strings = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getUserName());
        for (UserRole userRole : userRoleList){
            Role role = roleService.findByRoleId(userRole.getRoleId());
            strings.add(role.getRoleName());
        }
        map.put("roles", strings);
        map.put("userId", user.getUserId());
        return ReturnResult.success(map);
    }

    //退出登录
    @RequestMapping(value = "/logout")
    public Object logout() {
        log.info("退出登录");
        Subject subject = SecurityUtils.getSubject();
        try {
            /**
             * 退出登录后，删除用户在线状态，需要带token
             */
            subject.logout();
            return LoginResult.successLogout();
        } catch (Exception e) {
            log.error(e.getMessage());
            return LoginResult.failLogin(401, "10007");
        }
    }

}
