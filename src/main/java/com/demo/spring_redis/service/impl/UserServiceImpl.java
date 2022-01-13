package com.demo.spring_redis.service.impl;

import com.demo.spring_redis.entity.User;
import com.demo.spring_redis.mapper.UserMapper;
import com.demo.spring_redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

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
        Integer loginTime = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        return userMapper.login(username, password, loginTime) != null;
    }

    /**
     * @Author yhx
     * @Description 用户注册
     * @Date 14:47 2022/1/13
     * @param username
     * @param password
     * @return void
     **/
    @Override
    public Boolean register(String username, String password) {
        if (userMapper.selectLiveUser(username) != null){
            return false;
        }
        else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            Integer now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
            user.setCreateTime(now);
            user.setLoginTime(now);
            return userMapper.register(user) == 1;
        }
    }




}
