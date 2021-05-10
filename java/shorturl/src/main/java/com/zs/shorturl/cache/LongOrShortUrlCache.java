package com.zs.shorturl.cache;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟db
 *
 * @author zs
 * @date 2021/5/10
 */
public class LongOrShortUrlCache {

    /**
     * 存储 短链接 -》长连接 映射
     */
    private static Map<String,String> shortToLongMap = new ConcurrentHashMap(1024);

    /**
     * 存储 长连接 -> 短链接 映射
     */
    private static Map<String,String> longToShortMap = new ConcurrentHashMap(1024);


    private static class Lock{};
    private static Object mutex = new Lock();


    /**
     * 向 shortToLongMap 中存放
     * @param shortUrl
     * @param longUrl
     */
    public static void putShortForKey(String shortUrl,String longUrl){
        if (!StringUtils.hasLength(shortUrl) || !StringUtils.hasLength(longUrl)){
            return;
        }
        synchronized (mutex){
           shortToLongMap.put(shortUrl,longUrl);
        }
    }

    /**
     * 通过短链接获取长连接
     * @param shortUrl
     * @return  长连接
     */
    public static String getLongUrl(String shortUrl){
        if (!StringUtils.hasLength(shortUrl)){
            return null;
        }
        return shortToLongMap.get(shortUrl);
    }

    /**
     * 向 longToShortMap 中存放
     * @param shortUrl
     * @param longUrl
     */
    public static void putLongForKey(String longUrl,String shortUrl){
        if (!StringUtils.hasLength(shortUrl) || !StringUtils.hasLength(longUrl)){
            return;
        }
        synchronized (mutex){
            longToShortMap.put(longUrl,shortUrl);
        }
    }

    /**
     *
     * @param longUrl
     * @return 短链接
     */
    public static String getShortUrl(String longUrl){
        if (!StringUtils.hasLength(longUrl)){
            return null;
        }
        return longToShortMap.get(longUrl);
    }



}
