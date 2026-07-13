package com.cn.scdt.utils;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


/**
 *redis工具类
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param longUrl
     * @return
     */
    public String getShortUrl(String longUrl){
        String urlkey = SecureUtil.md5(longUrl);
        String urlkeyStr = String.format("urlKeys:%s", urlkey);
        String shortUrlKey = (String) redisTemplate.opsForHash().get(urlkeyStr,"shortUrlKey");
        if(StringUtils.isEmpty(shortUrlKey)){
            shortUrlKey = ShortUuidUtils.generateShortUuid();
            redisTemplate.opsForHash().put(urlkeyStr,"shortUrlKey",shortUrlKey);
            redisTemplate.opsForHash().put(urlkeyStr,"longUrl",longUrl);
            redisTemplate.opsForValue().set(shortUrlKey,longUrl);
            redisTemplate.expire(urlkeyStr, 24, TimeUnit.HOURS);
            redisTemplate.expire(shortUrlKey, 24, TimeUnit.HOURS);
        }
        return shortUrlKey;
    }

    public String getLongUrl(String shortUrlKey){
        String result = "";
        if(!StringUtils.isEmpty(shortUrlKey)){
            result = (String)redisTemplate.opsForValue().get(shortUrlKey);
            return result;
        }
        return result;
    }



}
