package com.wangxb.wxbhome.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤请求链接，日志输出：请求地址，请求耗费时间
 * @author wangxb
 */
@WebFilter(filterName = "TimeFilter", urlPatterns = "/*", asyncSupported = true)    // 该注解省略了filterConfig配置
@Slf4j
public class TimeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 记录访问资源和请求来源
        String url = request.getRequestURL().toString();
        String referer = request.getHeader("referer");
        filterChain.doFilter(servletRequest,servletResponse);

        long end = System.currentTimeMillis();

        log.info("From {} url: {} use time {} ms",referer,url,end-start);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
