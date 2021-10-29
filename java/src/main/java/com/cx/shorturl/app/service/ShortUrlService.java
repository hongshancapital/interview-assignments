package com.cx.shorturl.app.service;

public interface ShortUrlService {

    /**
     * 生成短域名
     *
     * @param longUrl 原始url
     * @return 短域名
     */
    String generatorShortUrl(String longUrl);

    /**
     * 获取长url映射
     *
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}
