package com.example.question_bank.web;

import com.example.question_bank.dao.UserDAO;
import com.example.question_bank.pojo.User;
import com.example.question_bank.service.UserService;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0? 0:start;
        Page4Navigator<User> page = userService.list(start,size,5);
        return page;
    }
}
