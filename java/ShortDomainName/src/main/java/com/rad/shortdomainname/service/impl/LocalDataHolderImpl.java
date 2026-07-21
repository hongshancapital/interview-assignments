package com.rad.shortdomainname.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.rad.shortdomainname.service.DomainDataHolderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: 本地缓存
 * @date 2022-03-19 16:28:43
 */

@Slf4j
@Service
public class LocalDataHolderImpl implements DomainDataHolderService {

    private Cache<String, String> cache;

    @PostConstruct
    public void init() {
        cache =  Caffeine.newBuilder()
                // 数量上限
                .maximumSize(1024)
                // 过期机制
                .expireAfterWrite(5, TimeUnit.MINUTES)
//                // 弱引用key
//                .weakKeys()
                // 弱引用value
                .weakValues()
                // 剔除监听
                .removalListener((RemovalListener<String, String>) (key, value, cause) ->
                        log.info("key:" + key + ", value:" + value + ", 删除原因:" + cause))
                .build();
    }


    @Override
    public String getLongUrl(String shortUrl) {
        return get(shortUrl);
    }

    /**
     * 放入缓存
     */
    @Override
    public boolean putLongUrl(String shortUrl, String longUrl) {
        cache.put(shortUrl, longUrl);
        return true;
    }

    /**
     * 获取缓存
     */
    public String get(String key){
        return StringUtils.isEmpty(key)?null:cache.getIfPresent(key);
    }
}
