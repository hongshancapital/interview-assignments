package com.hongshan.interfacejob.service;

import java.util.Optional;

public interface MatchStorageService {

    /**
     * 根据短地址取长地址
     */
    Optional<String> getShortUrlByLongUrl(String longUrl);

    /**
     * 根据长地址取短地址
     */
    Optional<String> getLongUrlByShortUrl(String shortUrl);

    /**
     * 返回存储的配对数量
     */
    long getCacheSize();

    /**
     * 设置长短地址的对应关系
     */
    void setUrlMatch(String longUrl, String shortUrl);
}
