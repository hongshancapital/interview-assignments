package com.utils;

import com.constant.CommonConstants;
import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 * 1、设置两个缓存对象
 * 2、longUrlCache缓存长-短链接的对象
 * 3、shortUrlCache缓存短-长链接的对象
 */
public class GuavaUtil {
    private static Logger logger = LoggerFactory.getLogger(GuavaUtil.class);

    private static RemovalListener<Object, Object> removalListener = new RemovalListener<Object, Object>() {
        @Override
        public void onRemoval(RemovalNotification<Object, Object> removal) {
            logger.info(" [{}]被移除原因是 [{}] ",removal.getKey(),removal.getCause());
        }
    };
    /**
     * 设置长链接缓存
     */
    public static LoadingCache<Object,Object> longUrlCache = CacheBuilder.newBuilder()
            .initialCapacity(2) //缓存的初始容量
            .maximumSize(CommonConstants.MAX_CACHE_SIZE)    //缓存的最大值
//            .expireAfterAccess(CommonConstants.MAX_CACHE_DAYS, TimeUnit.DAYS)   //设置定时回收，在给定的时间内没有读/写的访问，则会回收
            .expireAfterWrite(CommonConstants.MAX_CACHE_DAYS,TimeUnit.DAYS)//设置定时回收，缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
            .removalListener(removalListener)   //移除监听事件，当缓存被移除时，会出发这个监听事件。
            .build(new CacheLoader<Object, Object>() {
                @Override
                public Object load(Object o) throws Exception {
                    return null;
                }
            });
    /**
     * 设置短链接缓存
     */
    public static LoadingCache<Object,Object> shortUrlCache = CacheBuilder.newBuilder()
            .initialCapacity(2)
            .maximumSize(CommonConstants.MAX_CACHE_SIZE)
//            .expireAfterAccess(CommonConstants.MAX_CACHE_SIZE, TimeUnit.MINUTES)
            .expireAfterWrite(CommonConstants.MAX_CACHE_DAYS,TimeUnit.DAYS)
            .removalListener(removalListener)
            .build(new CacheLoader<Object, Object>() {
                @Override
                public Object load(Object o) throws Exception {
                    return null;
                }
            });

    /**
     * 设置长-短缓存
     * @param key   长链接
     * @param value 短链接
     */
    public static void setLongUrlKey(Object key,Object value){
        longUrlCache.put(key,value);
        logger.info("长链接[{}]缓存成功",key);
    }

    /**
     * 根据长链接获取短链接
     * @param key   长链接
     * @param defultValue   如果为空时的默认值
     * @return
     */
    public static Object getLongUrlKey(Object key,Object defultValue){
        try {
            return longUrlCache.get(key);
        } catch (ExecutionException e) {
            logger.info("获取缓存异常",e);
        }catch (CacheLoader.InvalidCacheLoadException e){
            logger.info("没有从缓存中获取到长链接数据，返回为null----");
        }
        return defultValue;
    }

    /**
     * 设置短-长缓存
     * @param key   短链接
     * @param value 长链接
     */
    public static void setShortUrlKey(Object key,Object value){
        shortUrlCache.put(key,value);
        logger.info("短链接[{}]缓存成功",key);
    }

    /**
     * 根据短链接获取长链接
     * @param key   短链接
     * @param defultValue   如果为空时的默认值
     * @return
     */
    public static Object getShortUrlKey(Object key,Object defultValue){
        try {
            return shortUrlCache.get(key);
        } catch (ExecutionException e) {
            logger.info("获取短链接缓存异常",e);
        }catch (CacheLoader.InvalidCacheLoadException e){
            logger.info("没有从缓存中获取到短链接数据，返回为null----");
        }
        return defultValue;
    }
}
