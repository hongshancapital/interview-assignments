package com.scdt.shorturl.service;

/**
 * 短URL服务
 * @author niuyi
 * @since  2021-12-10
 */
public interface ShortUrlService {
    String toShortURL(String longUrl);

    String toLongUrl(String shortUrl);
}
