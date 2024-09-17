package com.youming.sequoia.sdn.apipublic.constant;

import com.youming.sequoia.sdn.apipublic.domain.LRUCache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 映射数据存储
 */
public class StoreConstant {

    //短域名 -> 长域名映射
    public static final ConcurrentHashMap<String, String> shortUrlMap = new ConcurrentHashMap<>();

    //LRUCache，用于拦截重复长域名
    public static final LRUCache<String, String> lruCache = new LRUCache<>(10000);

    
}
