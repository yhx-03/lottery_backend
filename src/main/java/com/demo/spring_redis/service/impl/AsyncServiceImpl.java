package com.demo.spring_redis.service.impl;

import com.demo.spring_redis.entity.Lottery;
import com.demo.spring_redis.entity.LotteryRecord;
import com.demo.spring_redis.entity.LotteryUser;
import com.demo.spring_redis.mapper.LotteryMapper;
import com.demo.spring_redis.mapper.LotteryRecordMapper;
import com.demo.spring_redis.mapper.LotteryUserMapper;
import com.demo.spring_redis.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @program: spring_redis
 * @description: 异步方法service
 * @author: yhx
 * @create: 2022-01-11 10:29
 **/
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    LotteryRecordMapper lotteryRecordMapper;
    @Autowired
    LotteryMapper lotteryMapper;
    @Autowired
    LotteryUserMapper lotteryUserMapper;

    /**
     * @Author yhx
     * @Description 插入抽奖记录
     * @Date 9:34 2022/1/11
     * @param
     * @return void
     **/
    @Override
    @Async("LotteryExecutor")
    public void insertLotteryRecord(Long userId, Long activityId) {
        LotteryRecord lotteryRecord = new LotteryRecord();
        lotteryRecord.setUserId(userId);
        lotteryRecord.setActivityId(activityId);
        Integer now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        lotteryRecord.setCreateTime(now);
        lotteryRecord.setAlterTime(now);
        lotteryRecordMapper.insertOne(lotteryRecord);
    }

    /**
     * @Author yhx
     * @Description 插入中奖信息
     * @Date 14:22 2022/1/12
     * @param userId
     * @param lottery
     * @return void
     **/
    @Override
    @Async("LotteryExecutor")
    public void insertLotteryUser(Long userId, Lottery lottery) {
        int now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        String entireLotteryName = lotteryMapper.getEntireLotteryName(lottery.getId());
        LotteryUser lotteryUser = new LotteryUser();
        lotteryUser.setUserId(userId);
        lotteryUser.setLotteryId(lottery.getId());
        lotteryUser.setCreateTime(now);
        lotteryUser.setAlterTime(now);
        lotteryUser.setLotteryContent(entireLotteryName);
        lotteryUserMapper.insertOne(lotteryUser);
    }

}
