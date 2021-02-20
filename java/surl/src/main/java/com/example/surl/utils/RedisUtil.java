package com.example.surl.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @author 杨欢
 */
@Component
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public static String preL = "url-L:";
    public static String preS = "url-S:";


    public RedisUtil(RedisTemplate<String, String> redisTemplate) {this.redisTemplate = redisTemplate;}


    /**
     * 读取缓存
     *
     * @param key
     *
     * @return
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * 写入缓存
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }


}
