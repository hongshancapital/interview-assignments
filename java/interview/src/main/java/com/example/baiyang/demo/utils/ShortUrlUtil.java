package com.example.baiyang.demo.utils;

import com.example.baiyang.demo.domain.UrlEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/15
 * @description: 域名服务工具类
 */
@Component
@Slf4j
public class ShortUrlUtil {

    @Value("${long2short.map.capacity}")
    private Integer long2ShortCapacity;

    @Value("${short2long.map.capacity}")
    private Integer short2LongCapacity;

    //存储短域名到长域名的映射关系
    private volatile LRUCache<String, String> long2ShortMap;

    //存储长域名到短域名的映射关系
    private volatile LRUCache<String, String> short2LongMap;

    @PostConstruct
    public void init() {
        long2ShortMap = new LRUCache<>(long2ShortCapacity);
        short2LongMap = new LRUCache<>(short2LongCapacity);
    }

    /**
     * 存储长域名-短域名，短域名-长域名的映射关系
     *
     * @param urlEntity
     * @return
     */
    public Boolean save(@Validated UrlEntity urlEntity) {
        long2ShortMap.put(urlEntity.getLongUrl(), urlEntity.getShortUrl());
        short2LongMap.put(urlEntity.getShortUrl(), urlEntity.getLongUrl());

        return true;
    }

    /**
     * 根据短域名信息获取长域名信息
     *
     * @param shortUrl
     * @return
     */
    public String getLongUrl(String shortUrl) {
        return short2LongMap.getOrDefault(shortUrl, "");
    }

    /**
     * 根据长域名信息获取短域名信息
     *
     * @param longUrl
     * @return
     */
    public String getShortUrl(String longUrl) {
        return long2ShortMap.getOrDefault(longUrl, "");
    }

}

//自定义LRU算法存储数据
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}

