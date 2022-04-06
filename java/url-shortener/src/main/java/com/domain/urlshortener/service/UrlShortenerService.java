package com.domain.urlshortener.service;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 1:32
 */
public interface UrlShortenerService {

    /**
     * 创建短链
     *
     * @param longUrl
     *          长链URL
     * @return
     *          短链URL
     */
    String createShortUrl(String longUrl);

    /**
     * 获取长链
     *
     * @param shortUrl
     *          短链URL
     * @return
     *          长链URL
     */
    String getLongUrl(String shortUrl);

}
