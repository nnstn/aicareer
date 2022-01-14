package com.wrt.aicareer.rbac.mapper;

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

public interface RbacUserMapper extends Mapper<RbacUser> {

    @Select("select * from rbac_user where 1=1 " +
            "and (username=#{userCode} " +
            "or telephone=#{userCode} or email =#{userCode})")
    List<RbacUser> getUserByCode(@Param("userCode") String userCode);

    @SelectProvider(type = RbacMapperSql.class, method = "selectUsers")
    List<RbacUser> selectUsers(RbacUser user);

    class RbacMapperSql{

        public String selectUsers(RbacUser rbacUser){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select * from rbac_user where 1=1");
            if(StringUtils.isNoneBlank(rbacUser.getUsername())){
                sqlBuilder.append(" and  article_title like CONCAT('%','"+rbacUser.getUsername()+"','%')");
            }
            return sqlBuilder.toString();
        }
    }
}
