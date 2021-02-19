/*
 * Copyright (C) 2018 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * redis 工具类
 */
@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 读取缓存k
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        String result = null;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            result = operations.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("redis exception");
        }

        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("redis exception");
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value, Integer expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            // 负数过期时间则永不过期
            if (expireTime != null && expireTime > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("redis exception");
        }
        return result;
    }

}