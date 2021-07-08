package com.wrt.aicareer.rbac.mapper;

import com.wrt.aicareer.po.RbacMenu;
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

public interface RbacMenuMapper extends Mapper<RbacMenu> {

    @Select("select * from rbac_menu where menu_name =#{menuName})")
    List<RbacUser> getMenuByCode(@Param("menuName") String menuName);
}
