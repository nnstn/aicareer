package com.wrt.aicareer.controller;

import com.wrt.aicareer.po.RbacMenu;
import com.wrt.aicareer.po.RbacRole;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.service.RbacMenuService;
import com.wrt.aicareer.service.RbacRoleService;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    RbacMenuService rbacMenuService;

    @PostMapping("/add")
    public JsonData register(@RequestBody RbacMenu menu){
        log.info("进入新增菜单方法");
        if(StringUtils.isBlank(menu.getMenuName())){
            return JsonData.fail("角色名称不能为空");
        }
        return rbacMenuService.addMenu(menu);
    }
    @PostMapping("/update")
    public JsonData update(@RequestBody RbacMenu menu){
        log.info("进入用户注册方法");
        if(StringUtils.isBlank(menu.getMenuName())){
            return JsonData.fail("角色名称不能为空");
        }
        return rbacMenuService.update(menu);
    }
    @PostMapping("/cancle")
    public JsonData cancle(@RequestBody Long menuId){
        log.info("进入用户注册方法");
        return rbacMenuService.cancle(menuId);
    }
}
