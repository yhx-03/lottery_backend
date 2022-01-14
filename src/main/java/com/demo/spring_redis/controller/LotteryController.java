package com.demo.spring_redis.controller;

import com.demo.spring_redis.entity.Lottery;
import com.demo.spring_redis.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LotteryController {

    @Autowired
    LotteryService lotteryService;

    @ResponseBody
    @GetMapping("/lotteries")
    public List<Lottery> selectByActivityIdLotteries(@RequestParam("activityId") Long activityId) {
        return lotteryService.selectByActivityIdLotteries(activityId);
    }

    @ResponseBody
    @PatchMapping("/lotteries/{activityId}")
    public Lottery userLotteryDirect(@PathVariable("activityId") Long activityId, @RequestParam("userId")Long userId) {
        return lotteryService.userLotteryDirect(userId, activityId);
    }

}
