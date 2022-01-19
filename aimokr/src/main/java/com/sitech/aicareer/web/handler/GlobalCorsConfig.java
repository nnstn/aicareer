package com.sitech.aicareer.web.handler;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 处理跨域请求的过滤器
 */
//@Configuration
public class GlobalCorsConfig {
    //@Bean  // 因为无法处理 chrome-extension
    public FilterRegistrationBean corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写 *，否则cookie就无法使用了
        config.addAllowedOrigin("http://eip.teamshub.com");
        config.addAllowedOrigin("http://aicp.teamshub.com");
        config.addAllowedOrigin("https://aicp.teamshub.com");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:63342");
        config.addAllowedOrigin("chrome-extension://*/*");

        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("POST");
        config.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configSource));
        // 这个顺序很重要哦，为避免麻烦请设置在最前
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        //3.返回新的CorsFilter.
        return bean;
    }
}
