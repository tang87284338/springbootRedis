package com.blurrylight.springbootredis.controller;

import com.blurrylight.springbootredis.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author will
 * @description
 * @date 2022-07-25 15:45
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private SecKillService secKillService;

    @GetMapping
    public String test() {
        redisTemplate.opsForValue().set("name", "xiaoMing");
        return (String) redisTemplate.opsForValue().get("name");
    }

    @GetMapping(value = "secKill")
    public boolean secKill(Integer productId, Integer userId) {
        return secKillService.doSecKill(productId, userId);
    }
}
