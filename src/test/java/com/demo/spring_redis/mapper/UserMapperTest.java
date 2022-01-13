package com.demo.spring_redis.mapper;

import com.demo.spring_redis.entity.User;
import com.demo.spring_redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void login() {
        Integer loginTime = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        User user = userMapper.login("yhx", "123456", loginTime);
        log.info(String.valueOf(user));
    }

    @Test
    void register() {
        User user = new User();
        Integer now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        user.setUsername("yhx");
        user.setPassword("123456");
        user.setStatus(0);
        user.setCreateTime(now);
        user.setLoginTime(now);
        Integer i = userMapper.register(user);
        log.info(String.valueOf(i));
    }
}