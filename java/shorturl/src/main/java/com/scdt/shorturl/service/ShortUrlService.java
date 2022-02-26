package com.scdt.shorturl.service;
/**
 * @author leo
 * @Description: interface
 * @date 2022/1/27 16:47
 */
public interface ShortUrlService {
    /**
     * 长域名转短域名
     *
     * @param url 原始域名
     * @return 短域名
     */
    String shorterUrl(String url);

    /**
     * 根据短域名获取原始长域名
     *
     * @param shortUrl 短域名
     * @return 原始长域名
     */
    String getOriginUrl(String shortUrl);
}
