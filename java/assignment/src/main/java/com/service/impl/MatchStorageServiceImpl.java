package com.service.impl;

import com.service.IMatchStorageService;
import com.utils.GuavaUtil;
import org.springframework.stereotype.Component;

@Component
public class MatchStorageServiceImpl implements IMatchStorageService {
    @Override
    public int getShortUrlCacheSize() {
        Long shortUrlCacheSize =  GuavaUtil.shortUrlCache.size();
        return shortUrlCacheSize.intValue();
    }

    @Override
    public int getLongUrlCacheSize() {
        Long longUrlCacheSize =  GuavaUtil.longUrlCache.size();
        return longUrlCacheSize.intValue();
    }

    @Override
    public void setShortLongUrlCache(String longUrl, String shortUrl) {
        GuavaUtil.setShortUrlKey(longUrl,shortUrl);
        GuavaUtil.setLongUrlKey(shortUrl,longUrl);
    }

    @Override
    public String getLongUrlCache(String shortUrl) {
        String longUrl = String.valueOf(GuavaUtil.getLongUrlKey(shortUrl,"-1"));
        if("-1".equals(longUrl)) {
            return null;
        }else{
            return longUrl;
        }
    }

    @Override
    public String getShortUrlCache(String longUrl) {
        String shortUrl = String.valueOf(GuavaUtil.getShortUrlKey(longUrl,"-1"));
        if("-1".equals(shortUrl)) {
            return null;
        }else{
            return shortUrl;
        }
    }
}
