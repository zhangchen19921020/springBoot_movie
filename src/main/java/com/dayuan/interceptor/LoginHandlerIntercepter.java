package com.dayuan.interceptor;

import com.alibaba.fastjson.JSON;
import com.dayuan.util.AjaxResult;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerIntercepter implements HandlerInterceptor {

    /**
     * preHandler ：在进入Handler(Controller)方法之前执行了，
     * 用于身份验证，如果没有登陆，拦截就不向下执行
     * 返回值为 false ，即可实现拦截；否则，返回true时，拦截不进行执行；
     * 该拦截器需要在spring-mvc.xml里面配置
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //用户认证
        System.out.println("会话验证....");

//        String requestURI = request.getRequestURI();

        //只有addOrder的时候需要用户登陆

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loginName") != null) {
            //登录成功
            return true;//请求响应继续向下传递,传递给servlet
        } else {
            //这里是不能使用重定向 要返回json
            //每个错误都用该给一个错误码
            response.setContentType("text/html;charset=uf=utf-8");
            response.getWriter().write(JSON.toJSONString(AjaxResult.fail(600, "用户未登陆")));
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
