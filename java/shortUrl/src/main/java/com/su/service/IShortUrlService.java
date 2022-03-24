package com.su.service;

/**
 * @author hejian
 * 短域名服务
 */
public interface IShortUrlService {

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * 1. URL非法 抛出异常
     * 2. URL已有短链接，返回短链接
     * 3. URL无短链接，生成短链接
     * @param url 长域名信息
     * @return 短域名信息
     */
    String getShortUrl(String url);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * 1. URL非法 抛出异常-参数非法
     * 2. 短链接已存在，返回长链接
     * 3. 短链接不存在，抛出异常-参数非法
     * @param shortUrl 短域名信息
     * @return 长域名信息
     */
    String getLongUrl(String shortUrl);
}
