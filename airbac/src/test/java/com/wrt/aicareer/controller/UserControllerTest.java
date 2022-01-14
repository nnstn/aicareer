package com.wrt.aicareer.controller;

import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.web.holder.RequestHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.yaml")
public class UserControllerTest {
    @Autowired
    UserController userController;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestHolder.add(mockHttpServletRequest);
        System.out.println("开始测试-----------------");
    }
    @Test
    public void register() {
        RbacUser rbacUser = new RbacUser();
        rbacUser.setUsername("wangjn_bj");
        rbacUser.setEmail("wangjn1130@163.com");
        rbacUser.setTelephone("13180879819");
        rbacUser.setPassword("123456");
        rbacUser.setLastRoleId("0");
        userController.register(rbacUser);
    }
    @Test
    public void login() throws Exception {
        RbacUser rbacUser = new RbacUser();
        rbacUser.setUsername("wangjn_bj");
        rbacUser.setEmail("wangjn1130@163.com");
        rbacUser.setTelephone("13180879819");
        rbacUser.setPassword("123456");
        rbacUser.setLastRoleId("0");
        userController.login(rbacUser);
    }
}