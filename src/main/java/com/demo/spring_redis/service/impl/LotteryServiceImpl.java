package com.demo.spring_redis.service.impl;

import com.demo.spring_redis.entity.Lottery;
import com.demo.spring_redis.entity.LotteryRecord;
import com.demo.spring_redis.entity.LotteryUser;
import com.demo.spring_redis.mapper.LotteryMapper;
import com.demo.spring_redis.mapper.LotteryRecordMapper;
import com.demo.spring_redis.mapper.LotteryUserMapper;
import com.demo.spring_redis.service.LotteryService;
import com.demo.spring_redis.utils.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    LotteryRecordMapper lotteryRecordMapper;
    @Autowired
    LotteryUserMapper lotteryUserMapper;
    @Autowired
    LotteryMapper lotteryMapper;
    @Autowired
    RedisDistributedLock redisDistributedLock;

    public static final int ALLPROB = 10000;

    /**
     * @Author yhx
     * @Description
     * @Date 9:30 2022/1/14
     * @param userId
     * @param activityId
     * @return com.demo.spring_redis.entity.Lottery
     **/
    @Override
    public Lottery userLotteryDirect(Long userId, Long activityId) {
        //生成存储后缀
        String suffix = Base64.getEncoder().encodeToString(activityId.toString().getBytes(StandardCharsets.UTF_8));
        // 生成redis中Lottery存储对应的key
        String redisLotteryKey = "lottery_lock_" + suffix;

        // 奖项
        Lottery lottery;

        // 用户可以多次参与轮盘抽奖
        // 通过redis实现抽奖，redis实现分布式锁
        // 加锁
        while (true) {
            if (redisDistributedLock.tryGetDistributedLock(redisLotteryKey, userId, 2, TimeUnit.SECONDS)) {
                // 抽奖
                lottery = LotteryOnePrizeDirect(userId, activityId);
                break;
            }
        }
        // 解锁
        redisDistributedLock.releaseDistributedLock(redisLotteryKey, userId);

        return lottery;
    }

    /**
     * @Author yhx
     * @Description (直接使用mysql, 用redis分布式锁保障不超卖)抽取奖项
     * @Date 9:09 2022/1/14
     * @param userId
     * @param activityId
     * @return com.demo.spring_redis.entity.Lottery
     **/
    public Lottery LotteryOnePrizeDirect(Long userId, Long activityId) {

        // 1.判断是否有谢谢参与商品
        // 2.筛选出所有有库存的商品
        // 3.通过概率实现随机抽奖
        // 4.减库存,插记录


        Lottery defaultLottery = lotteryMapper.selectByActivityIdDefaultLotteries(activityId);
        if  (defaultLottery != null) {
            return LotteryOnePrizeDirectWithDefault(defaultLottery, userId, activityId);
        }
        else {
            Lottery lottery = new Lottery();
            lottery.setId(0L);
            lottery.setCategory(0);
            lottery.setLotteryContent("谢谢参与");
            return LotteryOnePrizeDirectWithDefault(lottery, userId, activityId);
        }
    }

    /**
     * @Author yhx
     * @Description
     * @Date 14:59 2022/1/14
     * @param defaultLottery
     * @param userId
     * @param activityId
     * @return com.demo.spring_redis.entity.Lottery
     **/
    public Lottery LotteryOnePrizeDirectWithDefault(Lottery defaultLottery, Long userId, Long activityId){

        // 待读取的数据
        Lottery lotteryItem = null;

        // 读取该活动的奖项
        List<Lottery> lotteryItems = lotteryMapper.selectByActivityIdLotteriesWithoutDefault(activityId);

        int lastScope = 0;
        // 将数组随机打乱
        Collections.shuffle(lotteryItems);
        // 构造抽奖区间
        Map<Long, Integer[]> awardItemScope = new HashMap<Long, Integer[]>();

        // item.getProb()=50 (50 / 10000 = 0.005)
        // sum_inventory: 总概率
        int sum_prob = 0;
        for (Lottery item : lotteryItems) {
            // width为概率区间宽度（概率 = 真实概率 * 10000)
            int width = item.getProb();
            sum_prob += width;
            int currentScope = lastScope + new BigDecimal(width).intValue();
            awardItemScope.put(item.getId(), new Integer[]{lastScope + 1, currentScope});
            lastScope = currentScope;
        }

        defaultLottery.setProb(ALLPROB - sum_prob);
        int currentScope = lastScope + new BigDecimal(defaultLottery.getProb()).intValue();
        awardItemScope.put(defaultLottery.getId(), new Integer[]{lastScope + 1, currentScope});
        lotteryItems.add(defaultLottery);

        // 产生中奖随机数并抽奖
        int luckyNumber = new Random().nextInt(ALLPROB) + 1;
        Long luckyPrizeId = 0L;
        Set<Map.Entry<Long, Integer[]>> set = awardItemScope.entrySet();
        for (Map.Entry<Long, Integer[]> entry : set) {
            if (luckyNumber >= entry.getValue()[0] && luckyNumber <= entry.getValue()[1]) {
                luckyPrizeId = entry.getKey();
                break;
            }
        }

        //获得抽奖奖项
        for (Lottery item : lotteryItems) {
            if (item.getId().intValue() == luckyPrizeId) {
                lotteryItem = item;
                break;
            }
        }

        if (lotteryItem.getCategory() == 1) {
            // 库存减少1
            lotteryItem.decrInventory(1);
            // 更改奖项库存数据
            updateLotteryInventory(lotteryItem.getInventory(), lotteryItem.getId());
            // 插入抽奖记录数据
            insertLotteryRecord(userId, activityId);
            // 插入中奖用户数据
            insertLotteryUser(userId, lotteryItem);
            return lotteryItem;
        }
        else {
            return defaultLottery;
        }

    }

    /**
     * @Author yhx
     * @Description 用户抽奖,使用redis分布式锁防止mysql库存超卖(含redis缓存,存在数据不一致问题)
     * @Date 10:17 2022/1/11
     * @param userId
     * @param activityId
     * @return com.demo.spring_redis.entity.Lottery
     **/
    @Override
    @Deprecated
    public Lottery userLottery(Long userId, Long activityId) {

        //生成存储后缀
        String suffix = Base64.getEncoder().encodeToString(activityId.toString().getBytes(StandardCharsets.UTF_8));
        // 生成redis中Lottery存储对应的key
        String redisLotteryKey = "lottery_lock_" + suffix;
        String redisLotteriesKey = "lotteries_" + suffix;

        // 奖项
        Lottery lottery;

        // 用户可以多次参与轮盘抽奖
        // 通过redis实现抽奖，redis实现分布式锁，保证数据一致性
        // 加锁
        while (true) {
            if (redisDistributedLock.tryGetDistributedLock(redisLotteryKey, userId, 2, TimeUnit.SECONDS)) {
                // 抽奖
                lottery = LotteryOnePrize(activityId, redisLotteriesKey);
                break;
            }
        }
        // 解锁
        redisDistributedLock.releaseDistributedLock(redisLotteryKey, userId);

        // 依赖redis保障并发, 之后再插入数据库
        // 插入中奖用户数据
        insertLotteryUser(userId, lottery);
        // 更改奖项库存数据
        updateLotteryInventory(lottery.getInventory(), lottery.getId());
        // 插入抽奖记录数据
        insertLotteryRecord(userId, activityId);

        return lottery;
    }

    /**
     * @Author yhx
     * @Description (含redis缓存, 可能存在redis与mysql数据不一致情况)抽取奖项
     * @Date 14:42 2022/1/12
     * @param activityId
     * @param redisLotteriesKey
     * @return com.demo.spring_redis.entity.Lottery
     **/
    @Deprecated
    public Lottery LotteryOnePrize(Long activityId, String redisLotteriesKey) {

        HashOperations hashOperations = redisTemplate.opsForHash();
        // 待读取的数据
        Lottery lotteryItem = null;
        // 从redis中读取数据, 如无则为null
        Map<Long, Lottery> lotteryItemsMap = hashOperations.entries(redisLotteriesKey);
        List<Lottery> lotteryItems;

        // 说明还未加载到缓存中，从数据库加载，并且将数据缓存
        if (lotteryItemsMap.size() == 0 ) {
            // 读取该活动所有库存大于0的奖项
            lotteryItems = lotteryMapper.selectByActivityIdLotteriesWithoutDefault(activityId);
            // 向redis中写入奖项的概率，库存
            lotteryItems.stream().forEach(li -> {
                hashOperations.put(redisLotteriesKey, li.getId(), li);
            });
        }
        else {
            lotteryItems = new LinkedList<Lottery>();
            // 传入奖项列表
            lotteryItemsMap.forEach((k, v) -> {
                // 判断库存是否为空
                if (v.getInventory() > 0L) {
                    lotteryItems.add(v);
                }
                else {
                    // 删除库存为0的数据
                    hashOperations.delete(redisLotteriesKey, k);
                }
            });
        }

        int lastScope = 0;
        // 将数组随机打乱
        Collections.shuffle(lotteryItems);
        // 构造抽奖区间
        Map<Long, int[]> awardItemScope = new HashMap<>();
        // item.getProb()=50 (50 / 10000 = 0.005)
        // sum_inventory: 各个奖项总库存
        int sum_inventory = 0;
        for (Lottery item : lotteryItems) {
            int width = item.getProb() * item.getInventory();
            sum_inventory += width;
            int currentScope = lastScope + new BigDecimal(width).intValue();
            awardItemScope.put(item.getId(), new int[]{lastScope + 1, currentScope});
            lastScope = currentScope;
        }

        // 产生中奖随机数并抽奖
        int luckyNumber = new Random().nextInt(sum_inventory);
        Long luckyPrizeId = 0L;
        Set<Map.Entry<Long, int[]>> set = awardItemScope.entrySet();
        for (Map.Entry<Long, int[]> entry : set) {
            if (luckyNumber >= entry.getValue()[0] && luckyNumber <= entry.getValue()[1]) {
                luckyPrizeId = entry.getKey();
                break;
            }
        }

        //获得抽奖奖项
        for (Lottery item : lotteryItems) {
            if (item.getId().intValue() == luckyPrizeId) {
                lotteryItem = item;
                break;
            }
        }

        lotteryItem.decrInventory(1);

        hashOperations.delete(redisLotteriesKey, lotteryItem.getId());
        hashOperations.put(redisLotteriesKey, lotteryItem.getId(), lotteryItem);

        return lotteryItem;
    }

    /**
     * @Author yhx
     * @Description 插入抽奖记录
     * @Date 9:34 2022/1/11
     * @param
     * @return void
     **/
    public void insertLotteryRecord(Long userId, Long activityId) {
        LotteryRecord lotteryRecord = new LotteryRecord();
        lotteryRecord.setUserId(userId);
        lotteryRecord.setActivityId(activityId);
        Integer now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        lotteryRecord.setCreateTime(now);
        lotteryRecord.setAlterTime(now);
        lotteryRecordMapper.insertOne(lotteryRecord);
    }

    /**
     * @Author yhx
     * @Description 插入中奖信息
     * @Date 14:22 2022/1/12
     * @param userId
     * @param lottery
     * @return void
     **/
    public void insertLotteryUser(Long userId, Lottery lottery) {
        int now = Math.toIntExact(Calendar.getInstance().getTimeInMillis() / 1000);
        String entireLotteryName = lotteryMapper.getEntireLotteryName(lottery.getId());
        LotteryUser lotteryUser = new LotteryUser();
        lotteryUser.setUserId(userId);
        lotteryUser.setLotteryId(lottery.getId());
        lotteryUser.setCreateTime(now);
        lotteryUser.setAlterTime(now);
        lotteryUser.setLotteryContent(entireLotteryName);
        lotteryUserMapper.insertOne(lotteryUser);
    }

    public void updateLotteryInventory(Integer inventory, Long lotteryId) {
        lotteryMapper.updateInventory(inventory, lotteryId);
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

    /**
     * @Author yhx
     * @Description 查询某活动所有奖项
     * @Date 10:12 2022/1/14
     * @param activityId
     * @return java.util.List<com.demo.spring_redis.entity.Lottery>
     **/
    @Override
    public List<Lottery> selectByActivityIdLotteries(Long activityId) {
        return lotteryMapper.selectByActivityIdLotteriesWithoutDefault(activityId);
    }

}