package com.breo.common.helper;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Component
public class CacheManagerHelper {

	private final String EHCAHE_PATH = "/ehcache.xml";
	
    private final String CACHE_NAME = "localCache";
    
    private static CacheManager manager;
    
    private static Cache cache;
    
    @PostConstruct
    public void init() {
    	//缓存对象初始化
        manager = CacheManager.create(this.getClass().getResourceAsStream(EHCAHE_PATH));
        cache = manager.getCache(CACHE_NAME);
    }
    
    /**
     * 把key放入缓存中
     */
    public static void put(String key, Object value) {
        cache.put(new Element(key, value));
        flush();
    }

    /**
     * 根据key获取缓存元素
     */
    public static Object get(String key) {
        Element element = cache.get(key);
        return element != null ? element.getObjectValue() : null;
    }

    /**
     * 根据key移除缓存
     */
    public static void remove(String key) {
        cache.remove(key);
        flush();
    }

    /**
     * 构建内存与磁盘的关系
     */
    public static void flush() {
        cache.flush();
    }

    /**
     * 关闭缓存管理器
     */
    public static void shutdown() {
        manager.shutdown();
    }
	
	
}
