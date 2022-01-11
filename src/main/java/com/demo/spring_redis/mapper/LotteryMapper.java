package com.demo.spring_redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.spring_redis.entity.Activity;
import com.demo.spring_redis.entity.Lottery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LotteryMapper extends BaseMapper<Activity> {

    List<Lottery> selectLotteries();

    void insertOne(Lottery lottery);

    List<Lottery> selectByActivityIdLotteries(Long activityId);

}
