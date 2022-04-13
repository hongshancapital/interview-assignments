package com.sequoia.shorturl.web.service;

/**
 * @Author: xxx
 * @Description: TODO
 * @Date: 2022/1/3 22:58
 * @Version: 1.0.0
 */
public interface IUrlConvertorService {
    /**
     * 长链接转短链接
     *
     * @param longUrl 待转换的长链接
     * @return String
     */
    String longUrlToShortUrl(String longUrl);

    /**
     * 根据短链接,获取原长连接
     *
     * @param shortUrl 短链
     * @return String
     * @throws RuntimeException
     */
    String getLongUrlByShortUrl(String shortUrl);
}
