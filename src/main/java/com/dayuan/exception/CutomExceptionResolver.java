package com.dayuan.exception;

import com.alibaba.fastjson.JSON;
import com.dayuan.util.AjaxResult;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//统一异常处理
@Component
public class CutomExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
        ex.printStackTrace();
        String errorMsg = null;

        if (ex instanceof BizException) {
            errorMsg = ex.getMessage();
        } else {
            errorMsg = "系统异常，请稍后再试";
        }

        try {
            response.setContentType("text/html;charset=uf=utf-8");
            response.getWriter().write(JSON.toJSONString(AjaxResult.fail(600, "用户未登陆")));
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
