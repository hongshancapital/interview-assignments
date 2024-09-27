package com.scdt.service.impl;

import com.scdt.service.UrlService;
import com.scdt.util.LRUCache;
import com.scdt.util.ShortUrlUtil;
import org.springframework.stereotype.Service;

/**
 * Class
 *
 * @Author: lenovo
 * @since: 2021-12-16
 */
@Service
public class UrlServiceImpl implements UrlService {
    /**
     * 短url未找到长URL返回值
     */
    public static final String NOT_FOUND = "not found!";
    private static final int CACHE_SIZE = 50000;
    private final static LRUCache<String,String> URL_MAP = new LRUCache<>(CACHE_SIZE);
    @Override
    public String get(String shortUrl) {
        String url = URL_MAP.get(shortUrl);
        if(url!=null){
            return url;
        }
        return NOT_FOUND;
    }

    @Override
    public String put(String longUrl) {
        String shortUrl = ShortUrlUtil.shortUrl(longUrl);
        URL_MAP.put(shortUrl,longUrl);
        return shortUrl;
    }
}
