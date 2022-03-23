package com.polly.shorturl.service;

/**
 * @author polly
 * @date 2022.03.20 10:55:26
 */
public interface IShortUrlService {

    Integer insertShortUrl(String url);

    String getUrlByShortUrl(String url);
}
