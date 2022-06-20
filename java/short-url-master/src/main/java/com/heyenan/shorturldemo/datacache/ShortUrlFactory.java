package com.heyenan.shorturldemo.datacache;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author heyenan
 * @description 短域名映射存储工厂（单例模式）
 *
 * @date 2020/5/07
 */
@SpringBootApplication
public class ShortUrlFactory {

    public static final int size = 1200;

    /*** 短域名映射缓存*/
    private final Map<String, String> shortUrlFactory = new LRULinkedHashMap<String, String>(size);

    /*** 长域名映射缓存*/
    private final Map<String, String> longUrlFactory = new LRULinkedHashMap<String, String>(size);

    /**
     * 获取缓存工厂单例
     *
     * @return 缓存单例实体
     */
    public static ShortUrlFactory get() {
        return ShortUrlFactory.Holder.INSTANCE;
    }

    /**
     * 由内部类持有唯一实体工厂
     *
     */
    private static class Holder {
        private final static ShortUrlFactory INSTANCE = new ShortUrlFactory();
    }

    public Map<String, String> getShortUrlDataCache() {
        return shortUrlFactory;
    }

    public Map<String, String> getLongUrlDataCache() {
        return longUrlFactory;
    }

    /**
     * 缓存工厂获取长链接
     *
     * @param urlFactory 缓存变量
     * @param shortUrl 短链接
     * @return 短链接
     */
    public static String getOriginUrl(Map<String, String> urlFactory, String shortUrl) {
        if (urlFactory.containsKey(shortUrl)) {
            return urlFactory.get(shortUrl);
        } else {
            return "";
        }
    }

    /**
     * 缓存工厂获取短链接
     *
     * @param urlFactory 缓存变量
     * @param longUrl 长链接
     * @return 长链接
     */
    public static String getShortUrl(Map<String, String> urlFactory, String longUrl) {

        if(urlFactory.containsKey(longUrl)){
            return urlFactory.get(longUrl);
        }else {
            return "";
        }
    }

}