package com.demo.spring_redis.mapper;

import com.demo.spring_redis.entity.Lottery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class LotteryMapperTest {

    @Autowired
    LotteryMapper lotteryMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectLotteries() {
        List<Lottery> lotteries = lotteryMapper.selectLotteries();
        log.info(lotteries.toString());
    }

    @Test
    void insertOne() {
        Lottery lottery = new Lottery();
        lottery.setActivityId(1L);
        lottery.setLotteryContent("二等奖");
        Integer now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        lottery.setCreateTime(now);
        lottery.setAlterTime(now);
        lottery.setInventory(3);
        lottery.setSum(3);
        lottery.setProb(3);
        lotteryMapper.insertOne(lottery);
    }

    @Test
    void selectByActivityIdLotteries() {
        List<Lottery> lotteries = lotteryMapper.selectByActivityIdLotteries(1L);
        log.info(lotteries.toString());
    }
}