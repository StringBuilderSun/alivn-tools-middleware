package com.service.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.SimpleByteSource;

/**
 * 自定义密码凭证器
 * User: lijinpeng
 * Created by Shanghai on 2020/3/1
 */
public class ShiroCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //输入的密码
        Object inputPassword = new SimpleByteSource((char[]) token.getCredentials());
        //储存的密码
        Object storePassword = new SimpleByteSource((char[]) info.getCredentials());
        return equals(inputPassword, storePassword);
    }
}
