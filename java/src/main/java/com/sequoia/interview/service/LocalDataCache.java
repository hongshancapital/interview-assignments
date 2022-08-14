package com.sequoia.interview.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author 17612735387@163.com
 * @date 2022/8/13 11:30
 **/
@Service
@Slf4j
public class LocalDataCache implements DomainDataCache {


    private Cache<String, String> cache;

    @PostConstruct
    public void init(){
        cache = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100000000)
                //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
                .recordStats()
                //设置写缓存后n秒钟过期
                .expireAfterWrite(60, TimeUnit.MINUTES)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                //只阻塞当前数据加载线程，其他线程返回旧值
                //.refreshAfterWrite(13, TimeUnit.SECONDS)
                //设置缓存的移除通知
               /* .removalListener(notification -> {
                    log.info(notification.getKey() + " " + notification.getValue() + " 被移除,原因:" + notification.getCause());
                })*/
                //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build();
    }

    /**
     * @param shortUrl
     * @Description: get long url by short url
     * @Param: short url
     * @return: longUrl
     * @Author: yhzhang
     * @Date: 2022/8/13
     */
    @Override
    public String getLongUrl(String shortUrl) {
        return StringUtils.isEmpty(shortUrl)? null : cache.getIfPresent(shortUrl);
    }

    /**
     * @param shortUrl
     * @param longUrl
     * @Description: put key shortUrl value longUrl
     * @Param: shortUrl
     * @Param: longUrl
     * @return: longUrl
     * @Author: yhzhang
     * @Date: 2022/8/13
     */
    @Override
    public boolean putLongUrl(String shortUrl, String longUrl) {
        cache.put(shortUrl,longUrl);
        return true;
    }
}
