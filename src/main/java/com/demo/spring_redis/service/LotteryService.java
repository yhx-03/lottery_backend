package com.demo.spring_redis.service;

import com.demo.spring_redis.entity.Lottery;
import com.demo.spring_redis.entity.LotteryRecord;
import com.demo.spring_redis.entity.LotteryUser;

import java.util.List;

/**
 * @Author yhx
 * @Description 抽奖service
 * @Date 16:09 2022/1/6
 **/
public interface LotteryService {

    List<LotteryUser> selectAll(Long activityId);

    Lottery userLottery(Long userId, Long activityId);

    List<LotteryRecord> selectAllLotteryUser(Long activityId);
}
