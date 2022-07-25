package com.blurrylight.springbootredis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author will
 * @description
 * @date 2022-07-25 17:07
 */
@Service
public class SecKillService {

    @Resource
    private RedisTemplate redisTemplate;

    public boolean doSecKill(Integer productId, Integer userId) {
        if (Objects.isNull(productId) || Objects.isNull(userId)) {
            return false;
        }
        String productKey  = "secKill" + productId;
        String userKey = "user" + userId;
        //获取库存，如果库位为null，则说明秒杀未开始
        Object amount  = redisTemplate.opsForValue().get(productKey);
        if (Objects.isNull(amount)) {
            return false;
        }
        //判断如果商品库存数量小于1，则秒杀结束
        int amo = (Integer) amount;
        if (amo <= 0) {
            return false;
        }
        //判断用户是否重复秒杀
        if (redisTemplate.opsForSet().isMember(userKey, userId)) {
            return false;
        }
        //秒杀过程：库存-1，秒杀成功用户加入成功清单
        redisTemplate.opsForValue().set(productKey, --amo);
        redisTemplate.opsForSet().add(userKey, userId);
        return true;
    }
}
