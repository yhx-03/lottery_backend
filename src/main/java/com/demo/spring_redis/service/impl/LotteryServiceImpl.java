package com.demo.spring_redis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.spring_redis.entity.Lottery;
import com.demo.spring_redis.entity.LotteryRecord;
import com.demo.spring_redis.entity.LotteryUser;
import com.demo.spring_redis.mapper.ActivityMapper;
import com.demo.spring_redis.mapper.LotteryMapper;
import com.demo.spring_redis.mapper.LotteryRecordMapper;
import com.demo.spring_redis.service.AsyncService;
import com.demo.spring_redis.service.LotteryService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    LotteryRecordMapper lotteryRecordMapper;
    @Autowired
    LotteryMapper lotteryMapper;
    @Autowired
    AsyncService asyncService;

    private static final int mulriple = 10000;

    /**
     * @Author yhx
     * @Description 用户抽奖
     * @Date 10:17 2022/1/11
     * @param userId
     * @param activityId
     * @return com.demo.spring_redis.entity.Lottery
     **/
    @Override
    public Lottery userLottery(Long userId, Long activityId) {
        // 抽奖逻辑
        Lottery lottery = LotteryOnePrize(activityId);

        // 插入抽奖数据
        asyncService.insertLotteryRecord(userId, activityId);

        return lottery;
    }

    public Lottery LotteryOnePrize(Long activityId){
        Lottery lotteryItem = null;
        Object lotteryItemsObj = redisTemplate.opsForValue().get("lotteries");
        List<Lottery> lotteryItems;
        //说明还未加载到缓存中，同步从数据库加载，并且异步将数据缓存
        if (lotteryItemsObj == null) {
            lotteryItems = lotteryMapper.selectByActivityIdLotteries(activityId);
        } else {
            lotteryItems = (List<Lottery>) lotteryItemsObj;
        }
        int lastScope = 0;
        //将数组随机打乱
        Collections.shuffle(lotteryItems);
        Map<Long, int[]> awardItemScope = new HashMap<>();
        //item.getProb()=50 (50 / 10000 = 0.005)
        for (Lottery item : lotteryItems) {
            int currentScope = lastScope + new BigDecimal(item.getProb().toString()).intValue();
            awardItemScope.put(item.getId(), new int[]{lastScope + 1, currentScope});
            lastScope = currentScope;
        }
        int luckyNumber = new Random().nextInt(mulriple);
        Long luckyPrizeId = 0L;
        if (!awardItemScope.isEmpty()) {
            Set<Map.Entry<Long, int[]>> set = awardItemScope.entrySet();
            for (Map.Entry<Long, int[]> entry : set) {
                if (luckyNumber >= entry.getValue()[0] && luckyNumber <= entry.getValue()[1]) {
                    luckyPrizeId = entry.getKey();
                    break;
                }
            }
        }
        for (Lottery item : lotteryItems) {
            if (item.getId().intValue() == luckyPrizeId) {
                lotteryItem = item;
                break;
            }
        }
        return lotteryItem;
    }
    // struct {
    //
    // }
    //git remote add origin https://github.com/XX/XX
    //git push -u origin master
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //

    /**
     * @Author yhx
     * @Description 随机抽取用户
     * @Date 17:10 2022/1/6
     * @param activityId
     * @return java.util.List<com.demo.spring_redis.entity.LotteryUser>
     **/
    @Override
    public List<LotteryUser> selectAll(Long activityId) {
        // 获得具体抽奖的各个奖项
        List<Lottery> lotteries = lotteryMapper.selectByActivityIdLotteries(activityId);

        // 获得参与抽奖人, 将抽奖用户写入redis, 设置5分钟时限
        val setOperations = redisTemplate.opsForSet();
        selectAllLotteryUser(activityId)
                .stream()
                .forEach(u -> {
                    setOperations.add(activityId, u.getUserId());
                });
        redisTemplate.expire(activityId,5, TimeUnit.MINUTES);

        // 中奖用户list
        List<LotteryUser> lotteryUsers = new LinkedList<LotteryUser>();
        // 随机抽取用户
        for(Lottery l: lotteries) {
            // 抽取用户
            List<Long> list = new LinkedList<>();
            for(int i = 0; i < l.getSum();i ++){
                list.add((Long)setOperations.pop(activityId));
            }
            lotteryUsers.addAll(
                    list.stream()
                            .map(id -> {
                                LotteryUser lotteryUser = new LotteryUser();
                                lotteryUser.setUserId(id);
                                lotteryUser.setLotteryId(l.getId());
                                return lotteryUser;
                            })
                            .collect(Collectors.toSet())
            );
        }
        // 清除redis
        redisTemplate.delete(activityId);

        return lotteryUsers;
    }

    /**
     * @Author yhx
     * @Description 获得参与莫抽奖的所有用户
     * @Date 16:47 2022/1/6
     * @param activityId
     * @return java.util.List<com.demo.spring_redis.entity.LotteryRecord>
     **/
    @Override
    public List<LotteryRecord> selectAllLotteryUser(Long activityId){
        //获得参与抽奖的所有用户
        return lotteryRecordMapper.selectByActivityIdLotteryRecord(activityId);
    }

}