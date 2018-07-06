package com.abs.controller.interceptor;

import com.abs.constant.SessionKeyConst;
import org.springframework.asm.Handle;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if(request.getSession().getAttribute(SessionKeyConst.USER_INFO)!=null)
        return  true;
        //处理ajax  x-requested-with不为空则为ajax请求
        if(request.getHeader("x-requested-with")!=null){
            String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            response.setHeader("url", basePath + "/login/sessionTimeout");
        }else{
            request.getRequestDispatcher("/login/sessionTimeout").forward(request,response);
        }
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
