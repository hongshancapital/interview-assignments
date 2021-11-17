package com.icbc.gjljfl.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author :
 * @version v1.0
 * @date2020/11/16
 * @description
 */
@Service
public class RedisLockService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 获取redis锁
     *
     * @param key     key 前缀已加
     * @param timeout 失效时间 单位为分钟
     * @return
     */
    public Boolean getLock(String key, Long timeout) {
        Boolean lock = redisTemplate.boundValueOps(RedisKeyConstant.REDIS_LOCK + key).setIfAbsent("LOCK", timeout, TimeUnit.MINUTES);
        return lock;
    }

    /**
     * 释放redis锁
     *
     * @param key key
     * @return
     */
    public Boolean deleteLock(String key) {
        Boolean flag = redisTemplate.delete(RedisKeyConstant.REDIS_LOCK + key);
        return flag;
    }
}
