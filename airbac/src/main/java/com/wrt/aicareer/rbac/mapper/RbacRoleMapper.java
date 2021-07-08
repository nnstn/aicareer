package com.wrt.aicareer.rbac.mapper;

import com.wrt.aicareer.bean.RoleQueryBean;
import com.wrt.aicareer.po.RbacRole;
import com.wrt.aicareer.po.RbacUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 19:53
 */

public interface RbacRoleMapper extends Mapper<RbacRole> {

    @Select("select * from rbac_role where 1=1 and role_name=#{roleName} ")
    List<RbacUser> getRoleByRoleName(@Param("userCode") String roleName);

    @Select("SELECT * FROM rbac_role,rbac_user_role WHERE rbac_role.role_id = rbac_user_role.role_id AND rbac_user_role.user_id = =#{uid} ")
    List<RbacUser> selectRoleByUser(@Param("uid") String uid);

    @SelectProvider(type = RbacRoleMapperSql.class, method = "selectRoleQuery")
    List<RbacRole> selectRoleQuery(RoleQueryBean pageQuery);


    class RbacRoleMapperSql{

        public String selectRoleQuery(RoleQueryBean pageQuery){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select * from rbac_role where 1=1");
            if(StringUtils.isNoneBlank(pageQuery.getRoleName())){
                sqlBuilder.append(" and  role_name = '"+pageQuery.getRoleName()+"'");
            }
            if(StringUtils.isNoneBlank(pageQuery.getCreateUid())){
                sqlBuilder.append(" and  create_uid = "+pageQuery.getCreateUid()+" ");
            }
            if(StringUtils.isNoneBlank(pageQuery.getCreateUid())){
                sqlBuilder.append(" and  create_uid = "+pageQuery.getCreateUid()+" ");
            }
            return sqlBuilder.toString();
        }
    }
}
