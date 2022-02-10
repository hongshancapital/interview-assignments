package com.scdt.domain.service;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
public interface IDomainService {

    /**
     * 根据shortUrl 获取对应的longUrl
     * @param shortUrl
     * @return
     */
    String getLongUrlByShortUrl(String shortUrl);

    /**
     * 根据longUrl获取对应的shortUrl
     * @param longUrl
     * @return
     */
    String getShortUrlByLongUrl(String longUrl);
}
