package com.jenius.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by liyongjun on 17/3/2.
 */
public class AuthFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 判断是否是http请求
        if (!(request instanceof HttpServletRequest)
                || !(response instanceof HttpServletResponse)) {
            throw new ServletException(
                    "OncePerRequestFilter just supports HTTP requests");
        }
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(true);

        String[] strs = {  "login", "logout", "static","css","random" }; // 路径中包含这些字符串的,可以不用登录直接访问
        StringBuffer url = httpRequest.getRequestURL();

        /**
         * 过滤掉根目录
         */
        String path = httpRequest.getContextPath();
        String protAndPath = httpRequest.getServerPort() == 80 ? "" : ":"
                + httpRequest.getServerPort();
        String basePath = httpRequest.getScheme() + "://"
                + httpRequest.getServerName() + protAndPath + path + "/";
        if (basePath.equalsIgnoreCase(url.toString())) {
            filterChain.doFilter(request, response);
            return;
        }
        // 特殊用途的路径可以直接访问
        if (strs != null && strs.length > 0) {
            for (String str : strs) {
                if (url.indexOf(str) >= 0) {
                    filterChain.doFilter(request, response);
                    return ;
                }
            }
        }
        // 从session中获取用户信息
        Object loginInfo = session.getAttribute("user");
        if (null != loginInfo) {
            System.out.println("username "+loginInfo);
            // 用户存在,可以访问此地址
            filterChain.doFilter(request, response);
        } else {
            // 用户不存在,踢回登录页面
            String returnUrl = httpRequest.getContextPath() + "/login.html";
            httpRequest.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("text/html; charset=UTF-8"); // 转码
            httpResponse
                    .getWriter()
                    .println(
                            "<script language=\"javascript\">alert(\"您还没有登录，请先登录!\");if(window.opener==null){window.top.location.href=\""
                                    + returnUrl
                                    + "\";}else{window.opener.top.location.href=\""
                                    + returnUrl
                                    + "\";window.close();}</script>");
            return;
        }

    }

    public void destroy() {

    }
}

