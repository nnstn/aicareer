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
        if(StringUtils.isBlank(role.getRoleName())){
            return JsonData.fail("角色名称不能为空");
        }
        return rbacRoleService.create(role);
    }
    @PostMapping("/update")
    public JsonData update(@RequestBody RbacRole role){
        log.info("进入用户注册方法");
        if(StringUtils.isBlank(role.getRoleName())){
            return JsonData.fail("角色名称不能为空");
        }
        return rbacRoleService.update(role);
    }
}
