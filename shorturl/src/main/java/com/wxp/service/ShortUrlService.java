package com.wxp.service;

public interface ShortUrlService {

    /**
     * 获取短连接
     * @param longUrl 原始链接
     * @return 短连接
     */
    public String getShortUrl(String longUrl);

    /**
     * 根据短链接获取原始链接
     * @param shortUrl 短连接
     * @return 原始链接
     */
    public String getOriginUrl(String shortUrl);
}
