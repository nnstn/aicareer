package com.sitech.aicareer.web.filter;

import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 通用过滤器:用于并发请求计数.以及Request全局处理
 */
@Slf4j
public class CommonFilter  implements Filter {
    //访问计数器
    private AtomicLong accessCounter;
    //并发计数器
    private AtomicLong concurrencyCounter;
    //系统启动时间
    private String startDate;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("----------初始化计数器数据 start----------");
        accessCounter = new AtomicLong(0);
        concurrencyCounter = new AtomicLong(0);
        startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info("----------初始化计数器数据 end----------");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        accessCounter.incrementAndGet();
        concurrencyCounter.incrementAndGet();
        log.info("--------------------------------------");
        log.info("系统启动时间：{},当前系统访问次数:{},当前处理并发数:{}",startDate,accessCounter,concurrencyCounter);
        log.info("--------------------------------------");
        RequestHolder.add(request);

        // 响应标头指定 指定可以访问资源的URI路径
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        //响应标头指定响应访问所述资源到时允许的一种或多种方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        //设置 缓存可以生存的最大秒数
        response.setHeader("Access-Control-Max-Age", "3600");

        //设置  受支持请求标头
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        // 指示的请求的响应是否可以暴露于该页面。当true值返回时它可以被暴露
        response.setHeader("Access-Control-Allow-Credentials","true");

        if (request.getMethod().equals("OPTIONS")){
            log.info("》》》浏览器 》》预验请求");
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            chain.doFilter(request, response);
        }
        concurrencyCounter.decrementAndGet();
        RequestHolder.remove();
    }

    @Override
    public void destroy() {
    }

}
