package com.example.demo.dao;

import com.example.demo.core.exception.CommonRuntimeException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName CacheDB
 * @Description
 * @Author gongguanghui
 * @Date 2021/11/25 6:04 PM
 * @Version 1.0
 **/
@Component
public class CacheDB {
    private static final Cache<String, String> URL_CACHE = CacheBuilder.newBuilder()
//            .recordStats()//开始缓存统计
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())//设置并发级别
            .initialCapacity(128)//设置缓存最大容量
            .maximumSize(10000000)//设置缓存数量上限 1千万
//            .maximumWeight(1024 * 1024 * 1024 * 100)//100M空间大小
//            .weigher(new Weigher<String, String>() {//计算缓存占用
//                @Override
//                public int weigh(String key, String value) {
//                    return key.getBytes().length + value.getBytes().length;
//                }
//            })
//            .expireAfterWrite(1, TimeUnit.HOURS)//设置缓存过期时间
            .build();

    public String getLongURLFromCache(String shortURL) {
        try {
            return URL_CACHE.get(shortURL, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    // TODO: 2021/11/25 后期可以从DB加载数据
                    return "";
                }
            });
        } catch (ExecutionException e) {
            throw new CommonRuntimeException("获取缓存数据异常!");
        }
    }

    public void setCache(String shortURL, String longURL) {
        URL_CACHE.put(shortURL, longURL);
    }

    public ConcurrentMap getAll() {
        return URL_CACHE.asMap();
    }
}
