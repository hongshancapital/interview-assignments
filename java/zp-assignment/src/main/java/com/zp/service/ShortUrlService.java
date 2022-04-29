package com.zp.service;

/**
 * 短域名Service
 */
public interface ShortUrlService {

    /**
     * 根据长域名获取短域名
     * @param url 长域名
     * @return 短域名
     */
    String getShortUrl(String url);

    /**
     * 根据短域名获取长域名
     * @param url 短域名
     * @return 长域名
     */
    String getLongUrl(String url);
}
