package com.demo.spring_redis.service;

import com.demo.spring_redis.entity.User;

import java.util.List;

/**
 * @Author yhx
 * @Description 用户service
 * @Date 17:07 2022/1/6
 **/
public interface UserService {

    List<User> selectAll();

    User selectById(Long id);

}
