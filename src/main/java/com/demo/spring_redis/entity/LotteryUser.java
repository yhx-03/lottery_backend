package com.demo.spring_redis.entity;

import java.util.Objects;

/**
 * @program: spring_redis
 * @description: 中奖用户
 * @author: yhx
 * @create: 2022-01-10 10:56
 **/
public class LotteryUser {

    private Long id;
    private Long userId;
    private Long lotteryId;
    private String lotteryContent;
    private Integer createTime;
    private Integer alterTime;

    public LotteryUser() {
    }

    public LotteryUser(Long id, Long userId, Long lotteryId, String lotteryContent, Integer createTime, Integer alterTime) {
        this.id = id;
        this.userId = userId;
        this.lotteryId = lotteryId;
        this.lotteryContent = lotteryContent;
        this.createTime = createTime;
        this.alterTime = alterTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Long lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryContent() {
        return lotteryContent;
    }

    public void setLotteryContent(String lotteryContent) {
        this.lotteryContent = lotteryContent;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(Integer alterTime) {
        this.alterTime = alterTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryUser that = (LotteryUser) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(lotteryId, that.lotteryId) && Objects.equals(lotteryContent, that.lotteryContent) && Objects.equals(createTime, that.createTime) && Objects.equals(alterTime, that.alterTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, lotteryId, lotteryContent, createTime, alterTime);
    }

    @Override
    public String toString() {
        return "LotteryUser{" +
                "id:" + id +
                ", userId:" + userId +
                ", lotteryId:" + lotteryId +
                ", lotteryContent:'" + lotteryContent + '\'' +
                ", createTime:" + createTime +
                ", alterTime:" + alterTime +
                '}';
    }
}
