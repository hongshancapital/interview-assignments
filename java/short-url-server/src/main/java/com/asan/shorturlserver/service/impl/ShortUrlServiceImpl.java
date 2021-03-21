package com.asan.shorturlserver.service.impl;

import com.asan.shorturlserver.constants.Constants;
import com.asan.shorturlserver.service.ShortUrlService;
import com.asan.shorturlserver.utils.ShortUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author asan
 * @since 2021-03-19
 */
@Service
public class ShortUrlServiceImpl  implements ShortUrlService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String toShort(String url){
        String shortCode = hashUrl(url);
        return shortCode;
    }

    @Override
    public String getOriginUrl(String code) {
        ValueOperations operations = redisTemplate.opsForValue();
        Object originUrl = operations.get(Constants.SHORT_URL_KEY + code);
        return null != originUrl ? originUrl.toString() : "";
    }

    private String hashUrl(String url) {

        ValueOperations operations = redisTemplate.opsForValue();

        String shortCode = "";

        //自增序列法-不会出现碰撞-这里使用RedisAtomicLong实现，也可以使用mysql自增主键实现

        //redis自增, 一个id对应一个62进制字符串
        if(!redisTemplate.hasKey(Constants.ORIGIN_URL_KEY + url)) {

            String lock = Constants.ORIGIN_URL_KEY+url+"LOCK";
            Boolean locked = operations.setIfAbsent(lock, url, 3, TimeUnit.SECONDS);

            if(!locked){return "no lock";}

            RedisAtomicLong urlIdCounter = new RedisAtomicLong(Constants.CODE_KEY, redisTemplate);
            long counter = urlIdCounter.incrementAndGet();
            if(counter < ShortUrlUtil.LENGTH_4_NUM) {
                urlIdCounter.set(ShortUrlUtil.LENGTH_4_NUM);
                counter = ShortUrlUtil.LENGTH_4_NUM;
            }
            shortCode = ShortUrlUtil.encode62(counter);
            operations.set(Constants.ORIGIN_URL_KEY + url, counter);

            operations.set(Constants.SHORT_URL_KEY + shortCode, url);
            redisTemplate.delete(lock);
        } else {
            String counter = operations.get(Constants.ORIGIN_URL_KEY + url).toString();
            shortCode = ShortUrlUtil.encode62(Long.parseLong(counter));
        }
        return shortCode;
    }
}
