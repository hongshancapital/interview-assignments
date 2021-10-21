package com.shenshen.zhw.urlconverter.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 说明：简单本地缓存
 *
 * @author zhangwei
 * @date 2021/11/7 14:11
 */
public class LocalCache {

    /**
     * 缓存池
     */
    private Map<String, CacheObject> CACHE_DB = null;

    private int max_cache_size = 0;
    private float load_factor = 0.75f;
    private int current_size = 0;

    /**
     * 线程池，定时清理失效的缓存数据
     */
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(2);

    public LocalCache(int maxCacheSize) {
        max_cache_size = maxCacheSize;
        int capacity = (int) Math.ceil(max_cache_size / load_factor) + 1;
        CACHE_DB = new ConcurrentHashMap<>(capacity, load_factor);


    }

    /**
     * 读取缓存
     *
     * @param key 缓存key
     * @param <T> 缓存数据类型
     * @return 返回缓存数据
     */
    public <T> T get(String key) {
        CacheObject cacheObject = CACHE_DB.get(key);
        return cacheObject == null ? null : (T) cacheObject.data;

    }


    /**
     * 已缓存数据个数
     *
     * @return 缓存池中数据个数
     */
    public int size() {
        return current_size;
    }


    /**
     * 添加缓存数据，可以设置失效时间
     *
     * @param key    key
     * @param value  value
     * @param expire 缓存失效时间 毫秒
     */
    public void put(String key, Object value, Long expire) {

        try {
            synchronized (this) {
                while (current_size >= max_cache_size) {
                    System.out.println("wait ..."+current_size);
                    this.wait();
                }
                current_size++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("put ...");

        if (expire > 0) {
            // 定时清理缓存数据
            EXECUTOR.schedule(() -> {
                CACHE_DB.remove(key);
                synchronized(this){
                    current_size--;
                    this.notifyAll();
                }
                System.out.println("remove ..."+current_size);

            }, expire, TimeUnit.MILLISECONDS);
            CACHE_DB.put(key, new CacheObject(value, expire));
        } else {
            CACHE_DB.put(key, new CacheObject(value, -1L));
        }
    }


    /**
     * 缓存包装类
     */
    private static class CacheObject {
        private Object data;
        private Long expire;

        public CacheObject(Object data, Long expire) {
            this.data = data;
            this.expire = expire;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, CacheObject> entry : CACHE_DB.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }


}
