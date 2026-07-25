package com.wwc.demo.util;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description：基于concurrentHash的本地缓存工具类
 * 缓存删除基于timer定时器
 * @date：2022年04月27日 21时07分41秒
 * @author：汪伟成
**/
public class CacheUtil {

    //默认大小1024
    private static final int DEFAULT_CAPACITY = 1024;

    // 最大缓存大小10000
    private static final int MAX_CAPACITY = 10000;

    //默认缓存过期时间
    private static final long DEFAULT_TIMEOUT = 3600000;

    //1000毫秒
    private static final long SECOND_TIME = 1000;

    //存储缓存的Map
    private static final ConcurrentHashMap<String, Object> map;

    private static final Timer timer;

    static {
        map = new ConcurrentHashMap<>(DEFAULT_CAPACITY);
        timer = new Timer();
    }

    //私有化构造方法
    private CacheUtil() {

    }

    /**
     *     缓存任务清除类
     */
    static class ClearTask extends TimerTask {
        private String key;

        public ClearTask(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            CacheUtil.remove(key);
        }

    }

    //==================缓存的增删改查

    /**
     *     添加缓存
     */
    public static boolean put(String key, Object object) {
        if (checkCapacity()) {
            map.put(key, object);
            //默认缓存时间
            timer.schedule(new ClearTask(key), DEFAULT_TIMEOUT);
            return true;
        }
        return false;
    }

    /**
     *     判断容量大小
     */
    public static boolean checkCapacity() {
        return map.size() < MAX_CAPACITY;
    }


    /**
     *     删除缓存
     */
    public static void remove(String key) {
        map.remove(key);
    }

    /**
     *     清除所有缓存
     */
/*    public void clearAll() {
        if (map.size() > 0) {
            map.clear();
        }
        timer.cancel();
    }*/

    /**
     *     获取缓存
     */
    public static Object get(String key) {
        return map.get(key);
    }

    /**
     *     是否包含某个key
     */
    public static boolean isContainKey(String key) {
        return map.containsKey(key);
    }

    /**
     *     是否包含某个value
     */
    public static boolean isContainValue(String value) {
        return map.containsValue(value);
    }

    /**
     *    通过value找到唯一的key
     */
    public static String getKeyByValue(String value){
        String key="";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(value.equals(entry.getValue())){
                key=entry.getKey();
            }
        }
        return key;
    }
}
