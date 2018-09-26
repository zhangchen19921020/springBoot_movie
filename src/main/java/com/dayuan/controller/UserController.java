package com.dayuan.controller;


import com.dayuan.entity.User;
import com.dayuan.exception.BizException;
import com.dayuan.mapper.UserMapper;
import com.dayuan.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("api")
public class UserController {


    TimeDiff timeDiff = new TimeDiff();

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserMapper userMapper;


    @RequestMapping("login.do")
    @ResponseBody
    public AjaxResult login(HttpSession session, ModelMap modelMap,
                            @RequestParam("loginName") String loginName,
                            @RequestParam("pwd") String pwd) {

        //data: $("#form1").serialize() 属性值通过表单序列化传递 也会被spring识别
        try {
            User user = null;
            user = userMapper.selectByLoginName(loginName);
            String password = user.getPassword();
//            String pwdMD5 = MD5Tools.MD5(pwd);

            if (user != null && password.equals(pwd)) {

                session.setAttribute("loginName", loginName);
                return AjaxResult.success();//不需要返回一个user，尽量一个接口干一件事
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.fail("账号密码错误");
    }



    @PostMapping("sendCode.do")
    @ResponseBody
    public AjaxResult sendCode(HttpSession session, @RequestParam String email) {


        try {
            String validateCode = MyRandomUtil.getRandom(6);
            MailUtil.sendTextMail(email, "邮箱验证", "验证码：" + validateCode);

            long timeStampCode = System.currentTimeMillis();//验证码时间戳
            String validateCode_timeStamp = validateCode + '_' + timeStampCode;
            session.setAttribute("validateCode_timeStamp", validateCode_timeStamp);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.fail("邮件发送失败");
        }
        return AjaxResult.success();
    }

    /**
     * 注册接口 注册完毕 不需要登陆
     *
     * @param session
     * @param loginName
     * @param pwd
     * @param validateCode
     * @return
     */
    @PostMapping(value = "signup.do")
    @ResponseBody
    public AjaxResult signup(HttpSession session
            , @RequestParam String loginName
            , @RequestParam String pwd
            , @RequestParam String validateCode) {


        //验证用户名是否被注册
        User userLoginName = userMapper.selectByLoginName(loginName);
        if (userLoginName != null) {
            return AjaxResult.fail("该用户名已存在");
        }

        try {
            String validateCode_timeStamp = (String) session.getAttribute("validateCode_timeStamp");
            String[] strings = validateCode_timeStamp.split("_");
            String code = strings[0];//验证码

            //给验证码设置失效时间： 20秒
            long diff = timeDiff.diff(validateCode_timeStamp);
            if (diff >= 20) {
                return AjaxResult.fail("时间超时");
            }
            //检验验证码
            if (validateCode_timeStamp == null || !validateCode.equals(code)) {
                return AjaxResult.fail("验证码不正确");
            }
            //注册新用户
            User user = new User();
            user.setLoginName(loginName);
            user.setEmail(loginName);
            user.setPassword(MD5Tools.MD5(pwd));
            userMapper.insertUser(user);//注册成功
            session.removeAttribute("validateCode_session");//移除验证码

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.fail("注册失败");
        }

        //session放入loginName
        session.setAttribute("loginName", loginName);
        return AjaxResult.success();//注册成功


    }
}