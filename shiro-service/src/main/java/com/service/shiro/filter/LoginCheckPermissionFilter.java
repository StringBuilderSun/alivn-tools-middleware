package com.service.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * User: lijinpeng
 * Created by Shanghai on 2020/3/1
 */
@Slf4j
public class LoginCheckPermissionFilter extends AuthorizationFilter {

    //检查权限
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        //获取请求地址  判断是否允许该地址通过
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String url = request.getRequestURI();
            Subject subject = SecurityUtils.getSubject();
            return subject.isPermitted(url);
        } catch (Exception ex) {
            log.error("权限校验失败！", ex);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        //检查用户是否登陆 如果未登陆 引导用户跳转登陆
        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {
            //未登录 跳转登陆
            saveRequestAndRedirectToLogin(request, response);
        } else {
            return true;
        }

        return false;
    }
}
