package com.wrt.aicareer.service;

import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.rbac.mapper.RbacUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 19:51
 */
@Service
public class RbacUserService {

    @Resource
    RbacUserMapper rbacUserMapper;

    public JsonData register(RbacUser rbacUserPO){
        rbacUserPO.update();
        rbacUserMapper.insert(rbacUserPO);
        return JsonData.success(rbacUserPO);
    }
}
