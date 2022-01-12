package com.demo.spring_redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.spring_redis.entity.LotteryUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: spring_redis
 * @description:
 * @author: yhx
 * @create: 2022-01-11 17:24
 **/
@Mapper
public interface LotteryUserMapper extends BaseMapper<LotteryUser> {

    void insertOne(LotteryUser lotteryUser);

}
