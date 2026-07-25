package com.ccb.domain.generate.impl;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author: nieyy
 * @Date: 2021/7/24 19:02
 * @Version 1.0
 * @Description: 使用ConcurrentHashMap作为本地缓存。
 */

public class LocalCache {

    private static Logger logger = LoggerFactory.getLogger(LocalCache.class);

    /**
     *
     * @param maxMemory 缓存占用最大内存，单位MB
     */

    public LocalCache(long maxMemory){
        this.maxMemory = maxMemory * 1024 * 1024;
    }


    //缓存占用最大内存，单位字节
    private long maxMemory;

    //目前使用的内存数，单位字节
    private Long userMemory = 0L;

    /**
     * 缓存对象
     */
    private final Map<String, CacheObj>  CACHE_OBJECT_MAP = new ConcurrentHashMap<>();
    /**
     * 这个记录了缓存使用的最后一次的记录，最近使用的在最前面
     */
    private final List<String> CACHE_USE_LOG_LIST = new LinkedList<>();

    private Boolean isCleanTimeOut = false;

    private Boolean isCleanRecently = false;


    /**
     * 设置缓存
     */
    public void setCache(String cacheKey, Object cacheValue, long cacheTime) {
        Long ttlTime;
        if (cacheTime <= 0L) {
            ttlTime = -1L;
        }else{
            ttlTime = Instant.now().toEpochMilli() + cacheTime;
        }
        CacheObj cacheObj = new CacheObj(cacheValue, ttlTime);
        checkSize(cacheKey,cacheObj);
        CACHE_OBJECT_MAP.put(cacheKey, cacheObj);
        logger.info("have set key :" + cacheKey);
    }

    /**
     * 设置缓存
     */
//    public void setCache(String cacheKey, Object cacheValue) {
//        setCache(cacheKey, cacheValue, -1L);
//    }
    /**
     * 获取缓存
     */
    public Object getCache(String cacheKey) {
        CacheObj cacheObj = CACHE_OBJECT_MAP.get(cacheKey);
        if (checkCache(cacheKey,cacheObj)) {
            saveCacheUseLog(cacheKey);
            return cacheObj.getCacheValue();
        }
        return null;
    }

    /**
     * 删除所有缓存
     */
//    public void clear() {
//        logger.info("have clean all key !");
//        CACHE_OBJECT_MAP.clear();
//        synchronized (userMemory){
//            userMemory = 0L;
//        }
//    }

    /**
     * 删除某个缓存
     */
    public CacheObj deleteCache(String cacheKey) {
        CacheObj cacheValue = CACHE_OBJECT_MAP.remove(cacheKey);

        //计算KEY所占用的字节数
        long keySize = ObjectSizeCalculator.getObjectSize(cacheKey);
        //计算VALUE所占用的字节数
        long valueSize = ObjectSizeCalculator.getObjectSize(cacheValue);
        //减少当前内存使用量
        synchronized (userMemory){
            userMemory = userMemory - keySize - valueSize;
        }

        return cacheValue;
    }


//    public boolean isExist(String cacheKey) {
//        return checkCache(cacheKey);
//    }


    /**
     * 判断缓存在不在,过没过期
     */
//    private boolean checkCache(String cacheKey) {
//        CacheObj cacheObj = CACHE_OBJECT_MAP.get(cacheKey);
//        if (cacheObj == null) {
//            return false;
//        }
//        if (cacheObj.getTtlTime() == -1L) {
//            return true;
//        }
//        if (cacheObj.getTtlTime() < Instant.now().toEpochMilli()) {
//            deleteCache(cacheKey);
//            return false;
//        }
//
//        return true;
//    }

    /**
     * 判断缓存在不在,过没过期<
     */
    private boolean checkCache(String cacheKey,CacheObj cacheObj) {
        if (cacheObj == null) {
            return false;
        }
        if (cacheObj.getTtlTime() == -1L) {
            return true;
        }
        if (cacheObj.getTtlTime() < Instant.now().toEpochMilli()) {
            deleteCache(cacheKey);
            return false;
        }

        return true;
    }

    /**
     * 检查大小
     * 当前大小如果已经达到最大
     * 首先删除过期缓存，如果还是达到最大缓存
     * 再删除最久未使用缓存，如果还是达到最大缓存，抛异常
     * 增加当前使用的内存空间
     */
    private void checkSize(String cacheKey,CacheObj value) {

        //计算KEY所占用的字节数
        long keySize = ObjectSizeCalculator.getObjectSize(cacheKey);
        //计算VALUE所占用的字节数
        long valueSize = ObjectSizeCalculator.getObjectSize(value);

        synchronized (userMemory){
            if(userMemory + keySize + valueSize >= maxMemory ){
                deleteTimeOut();
            }

            if(userMemory + keySize + valueSize >= maxMemory ){
                deleteLRU();
            }

            if(userMemory + keySize + valueSize >= maxMemory ){
                throw new RuntimeException("url太大，内存空间不够");
            }
            //增加当前内存使用量
            userMemory = userMemory + keySize + valueSize;
        }

    }

    /**
     * 删除最近未使用的缓存，最多删除20个，
     */
    private void deleteLRU() {
        logger.info("delete least recently used run!");
        String cacheKey = null;

        int i =0;
        synchronized (CACHE_USE_LOG_LIST){
            while(CACHE_USE_LOG_LIST.size() > 0 ){
                if(i > 20 ){
                    break;
                }
                cacheKey = CACHE_USE_LOG_LIST.remove(CACHE_USE_LOG_LIST.size() - 1);
                deleteCache(cacheKey);
                i++;
            }
        }

        logger.info("delete least recently unused cache quantity is :" + i);
    }


    /**
     * 删除过期的缓存
     */
    synchronized void deleteTimeOut() {
       logger.info("delete time out run!");
       //每次检查数量
       int checkQuantity = 20;
       //每次删除数量
       int deleteQuantity = 0;
       //设置删除比例
       float deleteFactor = 0.75f;

       int i = 0;
       for(Map.Entry<String, CacheObj> entry : CACHE_OBJECT_MAP.entrySet()) {
           i++;
           if (entry.getValue().getTtlTime() < Instant.now().toEpochMilli() && entry.getValue().getTtlTime() != -1L) {
               deleteCache(entry.getKey());
               deleteQuantity++;
           }

           if(i == checkQuantity ){
               float f = (float)deleteQuantity/checkQuantity;
               if(f < deleteFactor ){
                   return;
               }else{
                   deleteQuantity = 0;
                   i =0;
               }
           }

       }

    }


    /**
     * 保存缓存的使用记录
     */
    private void saveCacheUseLog(String cacheKey) {
        synchronized (CACHE_USE_LOG_LIST){
            CACHE_USE_LOG_LIST.remove(cacheKey);
            CACHE_USE_LOG_LIST.add(0,cacheKey);
        }

    }


    class CacheObj {
        /**
         * 缓存对象
         */
        private Object CacheValue;
        /**
         * 缓存过期时间
         */
        private Long ttlTime;

        CacheObj(Object cacheValue, Long ttlTime) {
            CacheValue = cacheValue;
            this.ttlTime = ttlTime;
        }

        Object getCacheValue() {
            return CacheValue;
        }

        Long getTtlTime() {
            return ttlTime;
        }

    }


}



