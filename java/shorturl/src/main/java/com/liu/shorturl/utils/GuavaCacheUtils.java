package com.liu.shorturl.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.liu.shorturl.exception.BaseExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Description： guava cache缓存工具类
 * Author: liujiao
 * Date: Created in 2021/11/12 14:22
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
public class GuavaCacheUtils {

    private static final Logger logger = LoggerFactory.getLogger(GuavaCacheUtils.class);

    private static LoadingCache<String, Object> build = null;

    static {
        build = CacheBuilder.newBuilder()
                .maximumSize(1000)
                //定时回收：没有写访问小时分钟后失效清理
                .expireAfterWrite(12, TimeUnit.HOURS)
                //定时刷新缓存
                .refreshAfterWrite(30, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Object>() {
                    //get为空时候的处理
                    @Override
                    public Object load(String key) throws Exception {
                        return null;
                    }
                });
    }

    /**
     * 根据key获取缓存中的值
     * @param key
     * @return
     */
    public static Object get(String key) {
        try {
            return build.get(key);
        } catch (Exception e) {
            logger.error("load cache from guava cache failed, key: {}, error message: {}", key, e.getMessage());
        }
        return null;
    }

    /**
     * 设置键值对
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        build.put(key, value);
    }

    /**
     * 判断缓存中是否存在某个值
     * @param value
     * @return
     */
    public static boolean containsValue(Object value) {
        return build.asMap().containsValue(value);
    }

    /**
     * 根据值获取对应的key
     * @param value
     * @return
     */
    public static Object getKeyByValue(Object value) {
        ConcurrentMap<String, Object> map = build.asMap();
        for(String getKey: map.keySet()){
            if(map.get(getKey).equals(value)){
                return getKey;
            }
        }
        return null;
    }


}
