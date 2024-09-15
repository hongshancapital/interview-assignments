package com.example.homework.service;

/**
 * @description:
 * @author: Karl
 * @date: 2022/4/27
 */
public interface UrlService {
    /**
     * 获取长域名url
     *
     * @param shortUrl 短域名url
     * @return 长域名url
     */
    String getLongUrl(String shortUrl);

    /**
     * 保存长域名url
     *
     * @param longUrl 长域名url
     * @return 短域名url
     */
    String saveLongUrl(String longUrl);
}
