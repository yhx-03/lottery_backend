package com.demo.spring_redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.spring_redis.entity.LotteryRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LotteryRecordMapper extends BaseMapper<LotteryRecord> {

    void insertOne(LotteryRecord lotteryRecord);

    List<LotteryRecord> selectByActivityIdLotteryRecord(Long activityId);

    LotteryRecord selectByActivityIdAndUserIdLotteryRecord(Long activityId, Long userId);

}
