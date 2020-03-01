package com.service.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义数据域
 * User: lijinpeng
 * Created by Shanghai on 2020/3/1
 */
public class UserShiroRealm extends AuthorizingRealm {


    //角色权限校验
    //获取用户角色 权限等信息
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取认证用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        if (null == userName) {
            throw new AuthenticationException("未登陆!");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        roles.add("USER");
        permissions.add("USER:DELETED");
        //设置角色信息(可通过其他方式获取)
        info.setRoles(roles);
        //设置角色信息(可通过数据库 redis等方式获取)
        info.setStringPermissions(permissions);
        return info;
    }

    //用户登陆校验
    //用户登陆 认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        //数据库查一下
        if (null == userName) {
            throw new AuthenticationException("未知账号");
        }
        //假设从数据库查询密码
        String password = "123456";
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, password.toCharArray(), getName());
        return info;
    }
}
