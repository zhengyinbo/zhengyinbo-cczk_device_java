package com.bo.shirodemo.init;

import com.bo.shirodemo.entity.Role;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.entity.UserRole;
import com.bo.shirodemo.repository.*;
import com.bo.shirodemo.utils.Constant;
import com.bo.shirodemo.utils.Md5Util;
import com.bo.shirodemo.utils.Ognl;
import org.apache.shiro.ShiroException;
import org.apache.shiro.util.Initializable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description:
 * @Author yb zheng
 * @Date 2025/7/9 15:06
 * @Version 1.0
 */

public class Init implements Initializable {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public void init() throws ShiroException {
        User admin = userRepository.findByUserName("admin");
        if (Ognl.isEmpty(admin)) {
            // 创建管理员账户
            admin = new User();
            admin.setUserId(1L);
            admin.setUserName("admin");
            admin.setPassword(Md5Util.pwdEncr("130788CCZK"));
            admin.setStatus("正常");
            admin.setUserType(Constant.USER_TYPE_ADMIN);
            admin.setCreateTime(new Date());
            admin.setUpdateTime(new Date());
            admin.setRemark("超级管理员账号");
            userRepository.save(admin);

            // 创建角色
            Role role = new Role();
            role.setRoleId(1L);
            role.setRoleName("admin");
            roleRepository.save(role);

            Role role2 = new Role();
            role2.setRoleId(1L);
            role2.setRoleName("user");
            roleRepository.save(role2);

            UserRole userRole = new UserRole();
            userRole.setRoleId(1L);
            userRole.setUserId(1L);
            userRoleRepository.save(userRole);
        }
    }
}
