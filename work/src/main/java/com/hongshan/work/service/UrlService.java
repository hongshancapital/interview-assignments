package com.hongshan.work.service;

public interface UrlService {
    /**
     * 根据长域名链接获取短域名
     *
     * @param longUrl
     * @return
     */
    String getShortUrl(String longUrl);

    /**
     * 根据短域名获取长域名链接
     *
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}