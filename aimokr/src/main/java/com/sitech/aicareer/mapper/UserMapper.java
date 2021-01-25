package com.sitech.aicareer.mapper;

import com.sitech.aicareer.pojo.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
    @Select("select * from user")
    List<User> getAllUser();

    @Select("select * from user where user.user_code = #{userCode} or user.email = #{userCode}  ")
    List<User> getUserByUserCode(String userCode);
}
