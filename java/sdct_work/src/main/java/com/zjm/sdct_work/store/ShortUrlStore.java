package com.zjm.sdct_work.store;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author:   billzzzhang
 * Date:     2022/3/19 下午4:24
 * Desc:
 */
@Component
public class ShortUrlStore implements ShortcutRepo {

    @Value("${shortcut.maxsize}")
    private int maxSize;


    private Cache<String, String> url2ShortcutCache;
    private Cache<String, String> shortcut2UrlCache;


    @PostConstruct
    public void init() {
        url2ShortcutCache = Caffeine.newBuilder().initialCapacity(0).maximumSize(maxSize).build();
        shortcut2UrlCache = Caffeine.newBuilder().initialCapacity(0).maximumSize(maxSize).build();
    }

    public String getShortcutByUrl(String url) {
        return url2ShortcutCache.getIfPresent(url);
    }

    public String getUrlByShortcut(String shortcut) {
        return shortcut2UrlCache.getIfPresent(shortcut);
    }


    public void storeShortcutByUrl(String url, String shortcut) {
        url2ShortcutCache.put(url, shortcut);
    }


    public void storeUrlByShortcut(String shortcut, String url) {
        shortcut2UrlCache.put(shortcut, url);
    }

}
