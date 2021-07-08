package com.wrt.aicareer.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wrt.aicareer.bean.RoleQueryBean;
import com.wrt.aicareer.po.RbacRole;
import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.power.bean.PageResult;
import com.wrt.aicareer.power.constants.ConstantRbac;
import com.wrt.aicareer.power.utils.RegexUtils;
import com.wrt.aicareer.rbac.mapper.RbacRoleMapper;
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
    RbacRoleMapper rbacRoleMapper;

    /**
     * 分页查询
     */
    public PageResult<RbacRole> selectRoleQuery(RoleQueryBean pageQuery) {
        if(pageQuery.getSortBy()==null||pageQuery.getSortBy().length()==0){
            pageQuery.setSortBy("last_update_time");
        }
        // 分页
        PageHelper.startPage(pageQuery.getPageNo(), pageQuery.getPageSize(), pageQuery.getSortBy() + (pageQuery.getDesc() ? " DESC" : " ASC"));
        // 查询
        List<RbacRole> roles = rbacRoleMapper.selectRoleQuery(pageQuery);

        PageInfo<RbacRole> info = new PageInfo<>(roles);
        // 解析分页结果
        return new PageResult<RbacRole>(info.getTotal(), roles);

    }
    /**
     * 创建角色
     */
    public JsonData create(RbacRole role) {
        role.create();
        rbacRoleMapper.insert(role);
        return JsonData.success(role);
    }
    /**
     * 更新角色
     */
    public JsonData update(RbacRole role) {
        role.update();
        rbacRoleMapper.updateByPrimaryKey(role);
        return JsonData.success(role);
    }

    /**
     * 真删除角色
     */
    public JsonData deleteRole(Long roleId){
        rbacRoleMapper.deleteByPrimaryKey(roleId);
        return JsonData.success("删除成功");
    }

    /**
     * 作废角色
     */
    public JsonData cancle(Long roleId) {
        RbacRole role = rbacRoleMapper.selectByPrimaryKey(roleId);
        role.update();
        role.setStatus(ConstantRbac.STATUS_UNUSE);
        rbacRoleMapper.updateByPrimaryKey(role);
        return JsonData.success(role);
    }
}
