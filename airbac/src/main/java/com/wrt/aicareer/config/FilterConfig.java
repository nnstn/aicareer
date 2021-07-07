package com.wrt.aicareer.config;

import com.wrt.aicareer.web.filer.GlobalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 21:41
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<GlobalFilter> globalFilter(){
        FilterRegistrationBean<GlobalFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new GlobalFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
