package com.bo.shirodemo.config;

import com.bo.shirodemo.entity.RolePermission;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.entity.UserRole;
import com.bo.shirodemo.service.*;
import com.bo.shirodemo.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private PermissionService permissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        log.info("[权限配置 MyShiroRealm.doGetAuthorizationInfo()]");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();
        Long userId = userService.findByUserName(username).getUserId();
        List<UserRole> roles = userRoleService.findByUserId(userId);
        for (int i = 0; i < roles.size(); i++) {
            String roleName = roleService.findByRoleId(roles.get(i).getRoleId()).getRoleName();
//            String rolePremission = roleRepository.findByRid(roles.get(i).getRid()).getPermissions();
            // 添加角色
            authorizationInfo.addRole(roleName);
            //添加权限
            List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(roles.get(i).getRoleId());
            for (int j = 0; j < rolePermissions.size(); j++) {
                String permissionName = permissionService.findByPermissionId(rolePermissions.get(j).getPermissionId()).getPermissionName();
                authorizationInfo.addStringPermission(permissionName);
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/

    /**
     * 认证
     *
     * @param auth
     * @return
     * @throws AuthenticationException 主体传过来的认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth)
            throws AuthenticationException {
        log.info("[身份认证 MyShiroRealm.doGetAuthenticationInfo()]，账号 = {}", (String) auth.getPrincipal());
        //从主体传过来的认证信息中获取用户名
        //String username = (String)token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        String username = (String) auth.getPrincipal();
        User user = userService.findByUserName(username);
        if (username == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo( //返回对象
                user.getUserName(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(Constant.PASSWORD_SALT),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
