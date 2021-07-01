package com.panx.modules.urlswitch.hanlder;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author PanX
 * @date 2021-07-01
 * 长短域名存储类
 *
 */
public class UrlSwitchHanlder {
    private static Cache<String, String> urlMatchCache = CacheBuilder.newBuilder().build();


    public static String getUrlMatch(String url) {
        synchronized (urlMatchCache) {
            return urlMatchCache.getIfPresent(url);
        }
    }

    public static void setUrlMatch(String longUrl, String shortUrl) {
        synchronized (urlMatchCache) {
            urlMatchCache.put(shortUrl, longUrl);
        }
    }
}
