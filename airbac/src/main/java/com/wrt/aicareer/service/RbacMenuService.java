package com.wrt.aicareer.service;

import com.alibaba.fastjson.JSON;
import com.wrt.aicareer.po.RbacMenu;
import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.power.constants.ConstantRbac;
import com.wrt.aicareer.rbac.mapper.RbacMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : wangjn_bj
 * @date : 2021/7/8 23:34
 */
@Service
public class RbacMenuService {

    @Resource
    RbacMenuMapper rbacMenuMapper;

    public JsonData addMenu(RbacMenu menu){
        List<RbacUser> menus = rbacMenuMapper.getMenuByCode(menu.getMenuName());
        if(null!=menus && menus.size()>0){
            return JsonData.fail("菜单名称已经被使用");
        }
        menu.create();
        rbacMenuMapper.insert(menu);
        return JsonData.success(menu);
    }
    public JsonData update(RbacMenu menu){
        menu.update();
        rbacMenuMapper.updateByPrimaryKey(menu);
        return JsonData.success("更新成功");
    }

    /**
     * 作废处理
     */
    public JsonData cancle(Long menuId){
        RbacMenu menu = rbacMenuMapper.selectByPrimaryKey(menuId);
        menu.update();
        menu.setStatus(ConstantRbac.STATUS_UNUSE);
        rbacMenuMapper.updateByPrimaryKey(menu);
        return JsonData.success("删除成功");
    }
    public JsonData deleteMenu(Long menuId){
        rbacMenuMapper.deleteByPrimaryKey(menuId);
        return JsonData.success("删除成功");
    }
}
