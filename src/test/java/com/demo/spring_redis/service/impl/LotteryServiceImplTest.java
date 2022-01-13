package com.demo.spring_redis.service.impl;

import com.demo.spring_redis.entity.Lottery;
import com.demo.spring_redis.entity.LotteryRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Slf4j
@SpringBootTest
class LotteryServiceImplTest {

    @Autowired
    LotteryServiceImpl lotteryService;
    @Autowired
    RedisTemplate redisTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectAllLotteryUser() {
        List<LotteryRecord> lotteryRecords = lotteryService.selectAllLotteryUser(1L);
        log.info(lotteryRecords.toString());
    }

    @Test
    void lotteryOnePrize() {

        Long activityId = 1L;

        //生成存储后缀
        String suffix = Base64.getEncoder().encodeToString(String.valueOf(activityId).getBytes(StandardCharsets.UTF_8));
        // 生成redis中Lottery存储对应的key
        String redisLotteriesKey = "lotteries_" + suffix;

        Lottery lottery = lotteryService.LotteryOnePrize(activityId, redisLotteriesKey);
        log.info(String.valueOf(lottery));
    }

    @Test
    void aaa() {
        redisTemplate.opsForValue().set("a", 1);
        Integer i = (Integer) redisTemplate.opsForValue().get("a");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(System.out);
            objectOutputStream.write(i);
            objectOutputStream.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void userLottery() {
        Long userId = 1L;
        Long activityId = 1L;
        Lottery lottery = lotteryService.userLottery(userId, activityId);
        log.info(String.valueOf(lottery));
    }
}