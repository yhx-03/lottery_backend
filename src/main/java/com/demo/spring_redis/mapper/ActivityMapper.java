package com.demo.spring_redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.spring_redis.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

}
