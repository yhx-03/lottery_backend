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
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (valueOperations.get(lockKey) == null) {
            valueOperations.set(lockKey, requestId, expireTime, TimeUnit.SECONDS);
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * @Author yhx
     * @Description 释放分布式锁
     * @Date 15:32 2022/1/12
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return boolean 是否释放成功
     **/
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        // lua脚本内容，依赖redis执行lua脚本的原子性·
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<Boolean>();
        defaultRedisScript.setResultType(Boolean.class);
        defaultRedisScript.setScriptText(script);
        Boolean result = redisTemplate.execute(defaultRedisScript, Collections.singletonList(lockKey), requestId);

        return result;

    }

}
