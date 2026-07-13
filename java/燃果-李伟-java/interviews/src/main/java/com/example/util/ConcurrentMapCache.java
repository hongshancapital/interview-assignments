package com.example.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 描述:
 * 同步缓存，支持失效时间
 *
 * @author eric
 * @create 2021-07-21 4:18 下午
 */
public class ConcurrentMapCache {
    /**
     * 缓存对象
     */
    private ConcurrentMap<String, CacheObject> cache = new ConcurrentHashMap<String, CacheObject>();

    /**
     * 默认删除时间(秒)
     */
    private static final int DEFAULT_DELETE_SECONDS = 5 * 60;

    /**
     * 定时删除器
     */
    private Timer deleteTimer = null;

    public ConcurrentMapCache(int deleteSeconds) {
        this.initTimer(deleteSeconds);
    }

    public ConcurrentMapCache() {
        this.initTimer(0);
    }

    /**
     * 初始化定时器
     *
     * @param deleteSeconds
     */
    private void initTimer(int deleteSeconds) {

        if (deleteSeconds == 0)
            deleteSeconds = DEFAULT_DELETE_SECONDS;

        deleteTimer = new Timer();
        deleteTimer.schedule(new DeleteTask(), 0, deleteSeconds * 1000);
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param expire 过期时间毫秒为单位
     */
    public void set(String key, Object value, long expire) {

        // ObjectUtils.isAnyEmpty 参数中只要存在empty即返回true
        if (!ObjectUtils.allNotNull(key, expire) || (expire < 0L && expire != -1))
            return;

        CacheObject cacheObject = new CacheObject(value, expire == -1L ? expire : System.currentTimeMillis() + expire);

        this.cache.put(key, cacheObject);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param expire -1L长期有效
     */
    public void put(String key, long expire) {

        if (!ObjectUtils.allNotNull(key, expire) || (expire < 0L && expire != -1))
            return;

        CacheObject cacheObject = this.cache.get(key);
        cacheObject.setTtl(expire == -1L ? expire : System.currentTimeMillis() + expire);
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {

        this.set(key, value, -1L);

    }

    /**
     * 获取缓存
     */
    public Object get(String key) {

        if (this.check(key)) {
            return this.cache.get(key).getValue();
        }
        return null;
    }

    /**
     * 删除某个缓存
     */
    public void delete(String key) {

        this.cache.remove(key);

    }


    /**
     * 判断缓存是否有效（过期、不存在均返回false）
     */
    private boolean check(String key) {

        CacheObject cacheObject = this.cache.get(key);

        if (cacheObject == null)
            return false;

        if (cacheObject.getTtl() == -1L)
            return true;

        if (cacheObject.getTtl() < System.currentTimeMillis()) {
            delete(key);
            return false;
        }

        return true;
    }

    /**
     * 定时删除任务
     */
    class DeleteTask extends TimerTask {

        @Override
        public void run() {

            // 循环Map中数据，找到已经过期的数据进行删除
            for(Map.Entry<String, CacheObject> entry : cache.entrySet()) {

                if (entry.getValue().getTtl() < System.currentTimeMillis() && entry.getValue().getTtl() != -1L) {
                    delete(entry.getKey());
                }
            }

        }
    }
}
