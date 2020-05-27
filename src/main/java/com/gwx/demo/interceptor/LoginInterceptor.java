package com.gwx.demo.interceptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object handler) throws  Exception{
        if(httpServletRequest.getSession().getAttribute("user")==null){
            httpServletResponse.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
