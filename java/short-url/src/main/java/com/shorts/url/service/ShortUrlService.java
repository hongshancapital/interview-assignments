package com.shorts.url.service;

/**
 * <p>
 * 短连接 service
 * </p>
 *
 * @author WangYue
 * @date 2022/3/21 18:10
 */
public interface ShortUrlService {

    String getShortUrl(String longUrl);

    String getLongUrl(String shortUrl);
}
