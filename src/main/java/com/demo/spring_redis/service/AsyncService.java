package com.demo.spring_redis.service;

/**
 * @program: spring_redis
 * @description:
 * @author: yhx
 * @create: 2022-01-11 10:29
 **/
public interface AsyncService {

    void insertLotteryRecord(Long userId, Long activityId);

}
