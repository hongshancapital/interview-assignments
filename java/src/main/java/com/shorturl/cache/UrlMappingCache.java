package com.shorturl.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by ruohanpan on 21/3/21.
 */
@Component
public class UrlMappingCache implements Cache {

    /**
     * 缓存过期时间(单位：分钟)
     */
    private static final Long KEY_EXPIRE = 60L;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void put(String code, String url) {
        redisTemplate.opsForValue().set(code, url, KEY_EXPIRE, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(url, code, KEY_EXPIRE, TimeUnit.MINUTES);
    }

    @Override
    public Long getCode(String url) {
        String code = get(url);
        return code == null ? null : Long.parseLong(code);
    }

    @Override
    public String getUrl(String code) {
        return get(code);
    }

    private String get(String key) {
        refreshExpiration(key);
        return redisTemplate.opsForValue().get(key);
    }

    private void refreshExpiration(String key) {
        redisTemplate.expire(key, KEY_EXPIRE, TimeUnit.MINUTES);
    }
}
