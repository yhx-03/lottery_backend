package com.demo.spring_redis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.spring_redis.entity.User;
import com.demo.spring_redis.mapper.UserMapper;
import com.demo.spring_redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * @Author yhx
     * @Description 查找所用用户
     * @Date 17:11 2022/1/6
     * @param
     * @return java.util.List<com.demo.spring_redis.entity.User>
     **/
    @Override
    public List<User> selectAll() {
        return userMapper.selectList(new QueryWrapper<User>());
    }

    /**
     * @Author yhx
     * @Description 根据id查找用户
     * @Date 17:11 2022/1/6
     * @param id
     * @return com.demo.spring_redis.entity.User
     **/
    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

}
