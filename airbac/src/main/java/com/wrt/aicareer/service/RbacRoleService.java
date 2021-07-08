package com.wrt.aicareer.service;

import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.power.utils.RegexUtils;
import com.wrt.aicareer.rbac.mapper.RbacUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 */
@Service
public class RbacRoleService {

    @Resource
    RbacUserMapper rbacUserMapper;

    /**
     * 用户注册
     */
    public JsonData register(RbacUser rbacUserPO){

        if(!RegexUtils.isMobile(rbacUserPO.getTelephone())
                || !RegexUtils.isEmail(rbacUserPO.getEmail())){
            return JsonData.fail("用户邮箱或手机号格式不对");
        }

        List<RbacUser> userByNameList = rbacUserMapper.getUserByCode(rbacUserPO.getUsername());
        if(userByNameList.size()>0){
            return JsonData.fail("此用户名已有人使用");
        }

        List<RbacUser> userByTelephoneList = rbacUserMapper.getUserByCode(rbacUserPO.getTelephone());
        if(userByTelephoneList.size()>0){
            return  JsonData.fail("此手机号已有人使用");
        }

        List<RbacUser> userByEmailList = rbacUserMapper.getUserByCode(rbacUserPO.getEmail());
        if(userByEmailList.size()>0){
            return  JsonData.fail("此用户名已有人使用");
        }

        rbacUserPO.update();
        rbacUserMapper.insert(rbacUserPO);
        return JsonData.success(rbacUserPO);
    }

    /**
     * 用户登录
     */
    public JsonData login(RbacUser user) {
        List<RbacUser> rbacUserList = rbacUserMapper.getUserByCode(user.getUsername());
        if(null==rbacUserList || rbacUserList.size() !=1 ){
            return JsonData.fail("用户账号异常");
        }
        if(user.getPassword().equalsIgnoreCase(rbacUserList.get(0).getPassword())){
            // 更新登录时间
            RbacUser rbacUser = rbacUserList.get(0);
            rbacUser.login();
            rbacUserMapper.updateByPrimaryKey(rbacUser);
            return JsonData.success(user);
        }
        return JsonData.fail("登录失败");
    }
}
