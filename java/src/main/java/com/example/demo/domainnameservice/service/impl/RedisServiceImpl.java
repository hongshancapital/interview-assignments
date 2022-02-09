package com.example.demo.domainnameservice.service.impl;

import com.example.demo.domainnameservice.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis service implementation.
 *
 * @author laurent
 * @date 2021-12-11 下午2:06
 */
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisServiceImpl(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * set key and value, expires when expire is set.
     *
     * @param key    mapping key.
     * @param value  mapping value.
     * @param expire expire time(milliseconds).
     */
    @Override
    public void setValue(String key, String value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * get value by key.
     *
     * @param key mapping key.
     * @return mapping value, null if not exists.
     */
    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
