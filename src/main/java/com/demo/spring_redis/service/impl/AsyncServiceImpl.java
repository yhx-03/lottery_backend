package com.demo.spring_redis.service.impl;

import com.demo.spring_redis.entity.LotteryRecord;
import com.demo.spring_redis.mapper.LotteryRecordMapper;
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

    /**
     * @Author yhx
     * @Description 插入抽奖记录
     * @Date 9:34 2022/1/11
     * @param
     * @return void
     **/
    @Async("LotteryRecordExecutor")
    public void insertLotteryRecord(Long userId, Long activityId) {
        LotteryRecord lotteryRecord = new LotteryRecord();
        lotteryRecord.setUserId(userId);
        lotteryRecord.setActivityId(activityId);
        Integer now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        lotteryRecord.setCreateTime(now);
        lotteryRecord.setAlterTime(now);
        lotteryRecordMapper.insertOne(lotteryRecord);
    }

}
