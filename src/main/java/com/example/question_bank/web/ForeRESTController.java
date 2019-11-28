package com.example.question_bank.web;

import com.example.question_bank.pojo.Collection;
import com.example.question_bank.pojo.PropertyValue;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.User;
import com.example.question_bank.service.CollectionService;
import com.example.question_bank.service.PropertyValueService;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.service.UserService;
import com.example.question_bank.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@RestController
public class ForeRESTController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    CollectionService collectionService;

    @PostMapping("forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String email = userParam.getEmail();

        User user = userService.get(email, userParam.getPassword());
        if (null == user) {
            String message = "账号密码错误";
            return Result.fail(message);
        } else {
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    @PostMapping("foreregister")
    public Object register(@RequestBody User user){
        String email = user.getEmail();
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);

        boolean exist = userService.isExist(email);

        if (exist){
            String message = "该邮箱已被注册";
            return Result.fail(message);
        }
        user.setName(name);
        userService.add(user);
        return Result.success();
    }

    @GetMapping("forecheckLogin")
    public Object checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return Result.success();
        return Result.fail("未登录");
    }

    @PostMapping("foresearch")
    public Object search( String keyword){
        if(null == keyword)
            keyword = "";
        return questionService.search(keyword);
    }



}
