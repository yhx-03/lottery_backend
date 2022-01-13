package com.demo.spring_redis.service.impl;

import com.demo.spring_redis.entity.User;
import com.demo.spring_redis.mapper.UserMapper;
import com.demo.spring_redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * @Author yhx
     * @Description 用户
     * @Date 14:47 2022/1/13
     * @param username
     * @param password
     * @return com.demo.spring_redis.entity.User
     **/
    @Override
    public Boolean login(String username, String password) {
        return userMapper.login(username, password) != null;
    }

    /**
     * @Author yhx
     * @Description 用户注册
     * @Date 14:47 2022/1/13
     * @param user
     * @return void
     **/
    @Override
    public Boolean register(User user) {
        return userMapper.register(user) == 1;
    }




}
