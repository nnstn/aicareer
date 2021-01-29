package com.sitech.aicareer.web.filter;

import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        accessCounter.incrementAndGet();
        concurrencyCounter.incrementAndGet();
        log.info("--------------------------------------");
        log.info("系统启动时间：{},当前系统访问次数:{},当前处理并发数:{}",startDate,accessCounter,concurrencyCounter);
        log.info("--------------------------------------");
        RequestHolder.add((HttpServletRequest) request);
        chain.doFilter(request,response);
        concurrencyCounter.decrementAndGet();
        RequestHolder.remove();
    }

    @Override
    public void destroy() {
    }

}
