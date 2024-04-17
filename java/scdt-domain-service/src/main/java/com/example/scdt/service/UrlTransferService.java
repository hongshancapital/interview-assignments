package com.example.scdt.service;

/**
 * 长短域名转换接口：声明转换方法
 */
public interface UrlTransferService {

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longUrl 长域名
     * @return 短域名
     */
    String longUrlToShortUrl(String longUrl);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     *
     * @param shortUrl 短域名
     * @return 长域名
     */
    String shortUrlToLongUrl(String shortUrl);

}
