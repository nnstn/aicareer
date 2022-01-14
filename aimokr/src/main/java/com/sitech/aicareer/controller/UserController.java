package com.sitech.aicareer.controller;

import com.sitech.aicareer.pojo.User;
import com.sitech.aicareer.service.UserService;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.power.bean.PageQuery;
import com.wrt.aicareer.power.bean.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("getall")
    public JsonData getAllUser(@RequestBody PageQuery pageQuery) {
        PageResult<User> pageResult = userService.getAllUser(pageQuery);
        return JsonData.success(pageResult);
    }

    @PostMapping("insert")
    public JsonData insertUser(@RequestBody User user) {
        userService.insertUser(user);
        return JsonData.success("添加成功");
    }

    @PostMapping("update")
    public JsonData updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return JsonData.success("更新成功");
    }

    @PostMapping("delete/{id}")
    public JsonData deleteTask(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return JsonData.success("删除成功");
    }
}
