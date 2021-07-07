package com.wrt.aicareer.controller;

import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.service.RbacUserService;
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
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    RbacUserService rbacUserService;

    @PostMapping("/register")
    public JsonData register(@RequestBody RbacUser user){
        if(StringUtils.isBlank(user.getUsername())){
            return JsonData.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(user.getTelephone())){
            return JsonData.fail("手机号不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return JsonData.fail("密码不能为空");
        }
        if(StringUtils.isBlank(user.getMail())){
            return JsonData.fail("邮箱不能为空");
        }
        return rbacUserService.register(user);
    }
}
