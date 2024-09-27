package com.example.shortUrl.dao;

import java.lang.reflect.Constructor;

/**
 * @Author HOPE
 * @Description 缓存map工厂类
 * @Date 2022/5/2 14:01
 */
public class CacheMapFactory {
    public static  volatile CacheMap cacheMap;
    public CacheMapFactory(){

    }
    public static CacheMap newCacheMap(){
        if(cacheMap == null){
            synchronized (CacheMap.class){
                if(cacheMap == null){
                    try {
                        Constructor constructor=CacheMap.class.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        cacheMap=(CacheMap)constructor.newInstance();
                    } catch (Exception e) {
                        throw new IllegalArgumentException("反射生成缓存类异常");
                    }
                }
            }
        }
        return cacheMap;
    }

}

