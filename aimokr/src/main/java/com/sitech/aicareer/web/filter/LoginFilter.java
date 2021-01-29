package com.sitech.aicareer.web.filter;


import com.sitech.aicareer.bean.JsonData;
import com.sitech.aicareer.pojo.User;
import com.sitech.aicareer.web.handler.JsonMapper;
import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User sysUser = (User) req.getSession().getAttribute("user");
        if (sysUser == null) {
            writeText(JsonMapper.obj2String(JsonData.fail("用户未登录")), resp);
            return;
        }
        RequestHolder.add(sysUser);
        filterChain.doFilter(servletRequest, servletResponse);
        RequestHolder.remove();
        return;
    }
    @Override
    public void destroy() {

    }

    /**
     * @param html     返回html内容
     * @param response 返回响应
     */
    protected void write(String html, HttpServletResponse response) {
        PrintWriter pw = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            pw = response.getWriter();
            if (!"".equals(html) && html != null) {
                pw.write(html);

            } else {
                pw.write("");
            }
        } catch (Exception e) {
            System.out.println("向浏览器写入html异常:");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * @param text     返回文本内容
     * @param response 返回响应
     */
    protected void writeText(String text, HttpServletResponse response) {
        PrintWriter pw = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            pw = response.getWriter();
            pw.write(text);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    protected String basePath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }

}
