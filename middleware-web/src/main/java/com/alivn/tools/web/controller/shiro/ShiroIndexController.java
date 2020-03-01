package com.alivn.tools.web.controller.shiro;

import com.alivn.tools.web.args.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: lijinpeng
 * Created by Shanghai on 2020/3/1
 */

@Controller
@Slf4j
@RequestMapping("/shiro")
public class ShiroIndexController {

    @RequestMapping("")
    public String index() {
        //登陆界面
        return "shiro/login";
    }


    @RequestMapping("user/checkLogin")
    @ResponseBody
    public Response<String> login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        Response<String> response = new Response<String>();
        log.info("接受到请求:userName:{},password:{}", userName, password);
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            response.setRspDesc("登陆成功");
        } catch (Exception e) {
            log.error("Login Error:", e);
            if (e instanceof IncorrectCredentialsException) {
                response.setRspDesc("密码错误");
            } else {
                response.setRspDesc("登录失败");
            }

        }
        return response;
    }


    @RequestMapping("user/logout")
    @ResponseBody
    public Response<String> logout()
    {
        Response<String> response = new Response<String>();
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            subject.logout();
        }
        response.setRspDesc("退出登陆成功");
        return response;
    }

    @RequestMapping("user/queryUserInfo")
    @ResponseBody
    public Response<String> queryLessonUsers() {
        //需要权限校验
        Response<String> response = new Response<String>();
        response.setRspDesc("查询成功");
        return response;

    }

}
