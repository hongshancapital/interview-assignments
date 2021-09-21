package com.service;

public interface IMatchStorageService {
    int getShortUrlCacheSize();
    int getLongUrlCacheSize();
    void setShortLongUrlCache(String longUrl,String shortUrl);
    String getLongUrlCache(String shortUrl);
    String getShortUrlCache(String longUrl);
}
