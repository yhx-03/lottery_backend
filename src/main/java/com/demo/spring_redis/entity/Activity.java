package com.demo.spring_redis.entity;

import java.util.Objects;

/**
 * @description: 抽奖活动
 * @author: yhx
 * @create: 2022-01-06 17:04
 **/
public class Activity {

    private Long id;
    private String activityName;
    private Integer startTime;
    private Integer endTime;
    private Integer createTime;
    private Integer loginTime;

    public Activity() {
    }

    public Activity(Long id, String activityName, Integer startTime, Integer endTime, Integer createTime, Integer loginTime) {
        this.id = id;
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
        this.loginTime = loginTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(activityName, activity.activityName) && Objects.equals(startTime, activity.startTime) && Objects.equals(endTime, activity.endTime) && Objects.equals(createTime, activity.createTime) && Objects.equals(loginTime, activity.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityName, startTime, endTime, createTime, loginTime);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id:" + id +
                ", activityName:'" + activityName + '\'' +
                ", startTime:" + startTime +
                ", endTime:" + endTime +
                ", createTime:" + createTime +
                ", loginTime:" + loginTime +
                '}';
    }
}
