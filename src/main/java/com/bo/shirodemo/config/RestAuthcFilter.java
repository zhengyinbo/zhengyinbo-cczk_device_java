package com.bo.shirodemo.config;


import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import java.io.IOException;

/**
 * @author zhengyinbo
 * @date 2025/9/1 00:16
 */
public class RestAuthcFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        httpResponse.getWriter().write("{\"code\":1001, \"msg\":\"未登录或登录已过期\"}");
        return false; // 拦截请求
    }
}