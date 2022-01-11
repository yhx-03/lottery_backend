package com.demo.spring_redis.service.impl;

import com.demo.spring_redis.entity.LotteryRecord;
import com.demo.spring_redis.entity.LotteryUser;
import com.demo.spring_redis.service.LotteryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class LotteryServiceImplTest {

    @Autowired
    LotteryService lotteryService;
    @Autowired
    RedisTemplate redisTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void select() {
        List<LotteryUser> lotteryUsers = lotteryService.selectAll(1L);
        log.info(lotteryUsers.toString());
    }

    @Test
    void aaa() {
        Object o = redisTemplate.opsForValue().getAndDelete("a");
        System.out.println(o);
    }

    @Test
    void selectAllLotteryUser() {
        List<LotteryRecord> lotteryRecords = lotteryService.selectAllLotteryUser(1L);
        log.info(lotteryRecords.toString());
    }
}