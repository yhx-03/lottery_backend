package com.demo.spring_redis.controller;

import com.demo.spring_redis.mapper.UserMapper;
import com.demo.spring_redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: spring_redis
 * @description: 用户controller
 * @author: yhx
 * @create: 2022-01-10 13:28
 **/
@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/users")
    public Boolean login(@RequestParam("username")String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    @ResponseBody
    @PostMapping("/users")
    public Boolean register(@RequestParam("username")String username, @RequestParam("password") String password) {
        return userService.register(username, password);
    }

}
