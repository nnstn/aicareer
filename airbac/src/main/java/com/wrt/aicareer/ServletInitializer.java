package com.wrt.aicareer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @classname	ServletInitializer 
 * @describe	Spring boot 工程部署到 tomcat 环境下使用
 * @author		wangjn_bj@si-tech.com.cn
 * @date		2018年5月23日 下午4:26:47 
 */
public class ServletInitializer extends SpringBootServletInitializer {
    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AiRbacStarter.class);
    }
}