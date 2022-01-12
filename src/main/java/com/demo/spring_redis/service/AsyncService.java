package com.demo.spring_redis.service;

import com.demo.spring_redis.entity.Lottery;

/**
 * @program: spring_redis
 * @description:
 * @author: yhx
 * @create: 2022-01-11 10:29
 **/
public interface AsyncService {

    void insertLotteryRecord(Long userId, Long activityId);

    void insertLotteryUser(Long userId, Lottery lottery);
}
