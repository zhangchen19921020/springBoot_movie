package com.dayuan.controller;

import com.dayuan.entity.User;
import com.dayuan.mapper.UserMapper;
import com.dayuan.util.AjaxResult;
import com.dayuan.util.NeoProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FirstController {

    @Resource
    private NeoProperties neoProperties;
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/hello")
    @ResponseBody
    public AjaxResult hello() {

        User user = userMapper.selectByLoginName("1111");
        System.out.println(user);
        System.out.println(neoProperties.getTitle());
        System.out.println(neoProperties.getDescription());
        return AjaxResult.success();
    }


    @RequestMapping("/myThymeleaf")
    public String myThymeleaf(ModelMap modelMap) {

        modelMap.addAttribute("str","testSpringBoot");
        List<String> lists = new ArrayList<>();
        lists.add("S");
        lists.add("p");
        lists.add("r");
        lists.add("B");
        lists.add("o");
        lists.add("o");
        lists.add("t");
        modelMap.addAttribute("SPRINGBOOT",lists);
        System.out.println("哈哈哈哈");
        return "myThymeleaf";
    }

}
