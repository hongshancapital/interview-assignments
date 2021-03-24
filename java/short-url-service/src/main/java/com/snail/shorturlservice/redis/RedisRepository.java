package com.snail.shorturlservice.redis;

import com.snail.shorturlservice.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("redisRepository")
public class RedisRepository {
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getLongUrl(String key) {
        String longUrl = this.stringRedisTemplate.opsForValue().get(key);
        if (null != longUrl && longUrl.length() > 0){
            //更新缓存过期时间
            int cacheTime = this.applicationProperties.getCacheTime();
            TimeUnit timeUnit = this.applicationProperties.getCacheTimeUnit();
            this.stringRedisTemplate.expire(key, cacheTime, timeUnit);
        }
        return longUrl;
    }

    public boolean delete(String key){
        return this.stringRedisTemplate.delete(key);
    }

    public void setShortUrl(String key, String longUrl) {
        int cacheTime = this.applicationProperties.getCacheTime();
        TimeUnit timeUnit = this.applicationProperties.getCacheTimeUnit();
        this.stringRedisTemplate.opsForValue().set(key, longUrl, cacheTime, timeUnit);
    }


}
