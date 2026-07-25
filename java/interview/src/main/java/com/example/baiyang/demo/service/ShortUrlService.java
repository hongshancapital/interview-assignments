package com.example.baiyang.demo.service;

/**
 * @Author: baiyang.xdq
 * @Date: 2021/12/15
 * @Description: 短域名服务接口
 */
public interface ShortUrlService {
    /**
     * 根据长域名信息获取短域名信息
     *
     * @param longUrl
     * @param digest
     * @return
     */
    String getShortUrl(String longUrl, String digest);

    /**
     * 根据短域名信息获取长域名信息
     *
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}
