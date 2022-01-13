package com.demo.spring_redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @program: spring_redis
 * @description: redis分布式锁
 * @author: yhx
 * @create: 2022-01-12 15:23
 **/
@Component
public class RedisDistributedLock {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * @Author yhx
     * @Description 尝试获取分布式锁
     * @Date 15:27 2022/1/12
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return boolean 是否获取成功
     **/
    public <T, V> boolean tryGetDistributedLock(T lockKey, V requestId, int expireTime, TimeUnit timeUnit) {

        // 判断lockkey, requestId非空
        if(lockKey == null || requestId == null){
            return false;
        }
        // 采用setifAbsent(即setnx方法)
        return redisTemplate.opsForValue().setIfAbsent(String.valueOf(lockKey), String.valueOf(requestId), expireTime, timeUnit);

    }

    /**
     * @Author yhx
     * @Description 释放分布式锁
     * @Date 15:32 2022/1/12
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return boolean 是否释放成功
     **/
    public <T, V> boolean releaseDistributedLock(T lockKey, V requestId) {

        // 判断lockkey, requestId非空
        if(lockKey == null || requestId == null){
            return false;
        }

        // lua脚本内容，依赖redis执行lua脚本的原子性·
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("if redis.call('get', KEYS[1]) == ARGV[1]");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("then");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("  return redis.call('del', KEYS[1])");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("else");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("  return 0");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("end");
        String script = stringBuilder.toString();
        DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<Boolean>();
        defaultRedisScript.setResultType(Boolean.class);
        defaultRedisScript.setScriptText(script);

        // redis执行lua脚本
        return redisTemplate.execute(defaultRedisScript, Collections.singletonList(String.valueOf(lockKey)), String.valueOf(requestId));

    }

}
