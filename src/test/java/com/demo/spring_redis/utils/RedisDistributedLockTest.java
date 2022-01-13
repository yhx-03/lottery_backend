package com.demo.spring_redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class RedisDistributedLockTest {

    @Autowired
    RedisDistributedLock redisDistributedLock;

    String lockKey = "q";
    String requestId = "1";
    Integer expireTime = 10;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void tryGetDistributedLock() {
        boolean result = redisDistributedLock.tryGetDistributedLock(lockKey, requestId, expireTime, TimeUnit.SECONDS);
        log.info(String.valueOf(result));
    }

    @Test
    void releaseDistributedLock() {
        boolean result = redisDistributedLock.releaseDistributedLock(lockKey, requestId);
        log.info(String.valueOf(result));
    }
}