package com.demo.spring_redis.controller;

import com.demo.spring_redis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @program: spring_redis
 * @description: 用户controller
 * @author: yhx
 * @create: 2022-01-10 13:28
 **/
@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;


}