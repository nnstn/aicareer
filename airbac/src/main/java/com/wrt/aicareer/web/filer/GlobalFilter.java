package com.wrt.aicareer.web.filer;

import com.wrt.aicareer.web.holder.RequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * filter 有坑filter调用排序问题或者是智能注册一个filter
 */
@Slf4j
public class GlobalFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig){

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		RequestHolder.add(request);
		filterChain.doFilter(servletRequest,servletResponse);
		RequestHolder.remove();
	}

	@Override
	public void destroy() {

	}
}
