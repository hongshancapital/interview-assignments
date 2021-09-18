package com.demo.shorturl.service;

/**
 * Description: 生成短域名，获取长域名 接口
 * @author : wangjianzhi
 * Create on 2021/9/17
 */
public interface ShortUrlService {

    /**
     * 功能描述: 通过长域名获取短域名
     * @param longUrl   长域名
     * @param maxRetryNum  短域名冲突重试次数
     * @return java.lang.String
     * @author wangjianzhi
     * @throws Exception
     * Create on 2021/9/17
     */
    String getShortUrl(String longUrl, int maxRetryNum) throws Exception;

    /**
     * 功能描述: 通过短域名获取长域名
     * @param shortUrl  短域名
     * @return java.lang.String
     * @author wangjianzhi
     * @throws Exception
     * Create on 2021/9/17
     */
    String getLongUrl(String shortUrl) throws Exception;
}