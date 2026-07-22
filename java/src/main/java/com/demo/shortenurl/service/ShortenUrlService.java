package com.demo.shortenurl.service;

/**
 * 短域名服务service接口
 * 提供两个接口getOriginalUrl和getShortenUrl，用户可以选择自己的实现，然后提供给controller
 */
public interface ShortenUrlService {
    /**
     * 短域名读取接口，通过短url查询到原始URL
     * @param shortenUrl 短URL
     * @return 原始URL
     */
    String getOriginalUrl(String shortenUrl);

    /**
     * 短域名存储接口，获取原始URL对应的短URL
     * @param originalUrl 原始URL
     * @return 短URL
     */
    String getShortenUrl(String originalUrl);
}
