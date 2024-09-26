package com.liujunpei.shorturl.service;

/**
 * @author 刘俊佩
 * @date 2022/1/25 下午5:21
 */
public interface ShortUrlService {
    /**
     * 将长域名转换为短域名
     * @param longUrl 待转换长域名
     * @return 根据长域名生成的短域名
     */
    String longUrlToShortUrl(String longUrl);

    /**
     * 根据短域名获取对应的长域名
     * @param shortUrl 短域名
     * @return 短域名对应的长域名
     */
    String shortUrlToLongUrl(String shortUrl);
}
