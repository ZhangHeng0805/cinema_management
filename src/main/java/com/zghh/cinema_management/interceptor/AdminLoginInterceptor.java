package com.zghh.cinema_management.interceptor;

import com.zghh.cinema_management.utils.Message;
import com.zghh.cinema_management.utils.TimeUtil;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class AdminLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录检查
        HttpSession session =request.getSession();
//        String requestURI = request.getRequestURI();
//        log.info("拦截的请求路径是{}",requestURI);
        Object loginAdmi = session.getAttribute("admin");
        if (loginAdmi!=null){
            //放行
            return true;
        }
        Message msg = new Message();
        msg.setTime(TimeUtil.time(new Date()));
        msg.setCode(404);
        msg.setTitle("未登录");
        msg.setMessage("请先登录");
        request.setAttribute("msg",msg);
        request.getRequestDispatcher("/login_admi").forward(request,response);
        //拦截
        return false;
    }
}
