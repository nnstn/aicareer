package com.wrt.aicareer.controller;

import com.wrt.aicareer.po.RbacRole;
import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.service.RbacRoleService;
import com.wrt.aicareer.service.RbacUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 21:52
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RbacRoleService rbacRoleService;

    @PostMapping("/add")
    public JsonData register(@RequestBody RbacRole role){
        log.info("进入用户注册方法");
        if(StringUtils.isBlank(user.getUsername())){
            return JsonData.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(user.getTelephone())){
            return JsonData.fail("手机号不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return JsonData.fail("密码不能为空");
        }
        if(StringUtils.isBlank(user.getEmail())){
            return JsonData.fail("邮箱不能为空");
        }
        return rbacUserService.register(user);
    }
    @PostMapping("/login")
    public JsonData login(@RequestBody RbacUser user){
        log.info("进入用户登录方法");
        if(StringUtils.isBlank(user.getUsername())){
            return JsonData.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return JsonData.fail("密码不能为空");
        }
        return rbacUserService.login(user);
    }
}
