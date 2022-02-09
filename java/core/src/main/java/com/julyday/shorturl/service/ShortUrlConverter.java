package com.julyday.shorturl.service;

public interface ShortUrlConverter {
    /**
     * 生成短域名方法，通过该函数可以生成短域名.
     *
     * @param longUrl 长域名.
     * @return shortUrl 短域名.
     */
    String generatorShortUrl(String longUrl);

    /**
     * 根据短域名获取长域名映射
     *
     * @param shortUrl 短域名.
     * @return longUrl 长域名.
     */
    String getLongUrl(String shortUrl);
}
