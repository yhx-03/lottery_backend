package com.demo.spring_redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @program: spring_redis
 * @description: 开启redis事务
 * @author: yhx
 * @create: 2022-01-13 15:37
 **/
@Configuration
public class RedisConfiguration {

    /**
     * @Author yhx
     * @Description 开启redis事务（仅支持单机，不支持cluster）
     * @Date 15:53 2022/1/13
     * @param factory
     * @return org.springframework.data.redis.core.RedisTemplate
     **/
    @Bean
    public RedisTemplate RedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    /**
     * @Author yhx
     * @Description description 配置事务管理器
     * @Date 15:53 2022/1/13
     * @param dataSource
     * @return org.springframework.transaction.PlatformTransactionManager
     **/
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}