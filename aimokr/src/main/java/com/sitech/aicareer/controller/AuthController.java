package com.sitech.aicareer.controller;

import com.sitech.aicareer.bean.JsonData;
import com.sitech.aicareer.pojo.User;
import com.sitech.aicareer.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("login")
    public JsonData login(@RequestBody User user, HttpServletRequest request) {
        user = authService.login(user);
        request.getSession().setAttribute("user",user);
        return JsonData.success("登录成功",user);
    }

    @PostMapping("register")
    public JsonData registerUser(@RequestBody User user,HttpServletRequest request) {

        String remoteHost = request.getRemoteHost();
        String remoteAddr = request.getRemoteAddr();

        log.info("remoteHost:"+remoteHost);
        log.info("remoteAddr:"+remoteAddr);

        user.setOperateIp(remoteAddr);


        authService.registerUser(user);

        return JsonData.success("用户注册成功");
    }

}
