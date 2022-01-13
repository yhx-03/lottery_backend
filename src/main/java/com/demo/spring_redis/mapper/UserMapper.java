package com.demo.spring_redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.spring_redis.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User login(String username, String password);

    Integer register(User user);

}
