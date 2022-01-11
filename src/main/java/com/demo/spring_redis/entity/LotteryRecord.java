package com.demo.spring_redis.entity;

import java.util.Objects;

/**
 * @Author yhx
 * @Description 抽奖记录
 * @Date 16:10 2022/1/6
 **/
public class LotteryRecord {

    private Long id;
    private Long userId;
    private Long activityId;
    private Integer createTime;
    private Integer alterTime;

    public LotteryRecord() {
    }

    public LotteryRecord(Long id, Long userId, Long activityId, Integer createTime, Integer alterTime) {
        this.id = id;
        this.userId = userId;
        this.activityId = activityId;
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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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
        LotteryRecord that = (LotteryRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(activityId, that.activityId) && Objects.equals(createTime, that.createTime) && Objects.equals(alterTime, that.alterTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, activityId, createTime, alterTime);
    }

    @Override
    public String toString() {
        return "LotteryRecord{" +
                "id:" + id +
                ", userId:" + userId +
                ", activityId:" + activityId +
                ", createTime:" + createTime +
                ", alterTime:" + alterTime +
                '}';
    }
}
