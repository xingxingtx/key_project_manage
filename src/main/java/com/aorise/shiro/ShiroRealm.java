package com.aorise.shiro;

import com.aorise.exceptions.UserCorrelationException;
import com.aorise.model.system.SysPermissionModel;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author:ZGP
 * @Desicription:实现AuthorizingRealm抽象类  身份认证doGetAuthenticationInfo  和授权doGetAuthorizationInfo
 * @Date:Created on 2018/7/9.
 * @Modified By:
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private com.aorise.service.system.SystemService SystemService;


    //授权，每一次访问都会授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("===========配置当前用户权限==========");
        SysUserModel use = (SysUserModel) principals.getPrimaryPrincipal();//doGetAuthenticationInfo方法注入什么对象就转什么对象
        //查询角色权限
        SysUserModel user = SystemService.findByUsername(use.getUserName());
        if(null == user){
            return null;
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (SysRoleModel role : user.getSysRoles()) {
            authorizationInfo.addRole(role.getName());  // 添加角色
            System.out.println("当前用户拥有的permission 权限 有:");
            for (SysPermissionModel per : user.getSysPermissions()) {
                authorizationInfo.addStringPermission(per.getCode());  // 添加具体权限
                System.out.println(""+per.getName()+";");
            }
        }
        return authorizationInfo;
    }

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("============= 登录认证=============");
        String username = (String) token.getPrincipal(); // 获取用户登录账号
        SysUserModel userInfo = SystemService.findByUsername(username);
        if(null == userInfo){
            throw new UnknownAccountException("用户不存在");
        }
        if(userInfo.getState()==2){
            throw new DisabledAccountException("用户已被冻结");

        }
        // 1). principal: 认证的实体信息. 可以是 username,也可以是数据表对应的用户的实体类对象,如果登录时需要返回用户的所有信息，则赋值用户对象
        Object principal = userInfo;
        // 2). credentials: 密码.
        Object credentials = userInfo.getPassWord();
        // 3). realmName: 当前 realm 对象的唯一名字. 调用父类的 getName() 方法
        String realmName = super.getName();
        // 4). credentialsSalt: 盐值. 类型是ByteSource
        ByteSource credentialsSalt = ByteSource.Util.bytes(userInfo.getUserName());//将用户名作为盐
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return info;
    }
}
