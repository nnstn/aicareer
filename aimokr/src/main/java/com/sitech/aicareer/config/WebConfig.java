package com.sitech.aicareer.config;

import com.sitech.aicareer.web.filter.CommonFilter;
import com.sitech.aicareer.web.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> filterRegistrationBean(){
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<LoginFilter>();
        bean.setFilter(new LoginFilter());
        bean.addUrlPatterns("/task/*");
        bean.addUrlPatterns("/camera/*");
        return bean;
    }
    @Bean
    public FilterRegistrationBean<CommonFilter> commonFilterRegistration(){
        FilterRegistrationBean<CommonFilter> bean = new FilterRegistrationBean<CommonFilter>();
        bean.setFilter(new CommonFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
