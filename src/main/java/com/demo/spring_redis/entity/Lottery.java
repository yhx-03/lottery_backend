package com.demo.spring_redis.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author yhx
 * @Description 抽奖奖项
 * @Date 16:10 2022/1/6
 **/
public class Lottery implements Serializable {

    private Long id;
    private Long activityId;
    private String lotteryContent;
    private Integer sum;
    private Integer inventory;
    // prob = 0.05 * 10000 = 50
    private Integer prob;
    private Integer createTime;
    private Integer alterTime;

    public Lottery() {
    }

    public Lottery(Long id, Long activityId, String lotteryContent, Integer sum, Integer inventory, Integer prob, Integer createTime, Integer alterTime) {
        this.id = id;
        this.activityId = activityId;
        this.lotteryContent = lotteryContent;
        this.sum = sum;
        this.inventory = inventory;
        this.prob = prob;
        this.createTime = createTime;
        this.alterTime = alterTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getLotteryContent() {
        return lotteryContent;
    }

    public void setLotteryContent(String lotteryContent) {
        this.lotteryContent = lotteryContent;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getProb() {
        return prob;
    }

    public void setProb(Integer prob) {
        this.prob = prob;
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
        Lottery lottery = (Lottery) o;
        return Objects.equals(id, lottery.id) && Objects.equals(activityId, lottery.activityId) && Objects.equals(lotteryContent, lottery.lotteryContent) && Objects.equals(sum, lottery.sum) && Objects.equals(inventory, lottery.inventory) && Objects.equals(prob, lottery.prob) && Objects.equals(createTime, lottery.createTime) && Objects.equals(alterTime, lottery.alterTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityId, lotteryContent, sum, inventory, prob, createTime, alterTime);
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id:" + id +
                ", activityId:" + activityId +
                ", lotteryContent:'" + lotteryContent + '\'' +
                ", sum:" + sum +
                ", inventory:" + inventory +
                ", prob:" + prob +
                ", createTime:" + createTime +
                ", alterTime:" + alterTime +
                '}';
    }

}
