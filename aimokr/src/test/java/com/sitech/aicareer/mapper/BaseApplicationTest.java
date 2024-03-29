package com.sitech.aicareer.mapper;

import com.sitech.aicareer.OfficeStarter;
import com.sitech.aicareer.web.handler.RequestHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= OfficeStarter.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
//@TestPropertySource("classpath:application.yaml")
public class BaseApplicationTest {

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestHolder.add(mockHttpServletRequest);
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}