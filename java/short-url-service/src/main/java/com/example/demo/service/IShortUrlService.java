package com.example.demo.service;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
public interface  IShortUrlService {

    /**
     * 长链接转短链接
     */
    String longUrlConvertShortUrl(String longUrl);

    /**
     * 根据短链接,获取原长连接
     */
    String shortUrlConvertLongUrl(String shortUrl);
}
