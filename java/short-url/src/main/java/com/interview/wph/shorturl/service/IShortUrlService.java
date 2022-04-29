package com.interview.wph.shorturl.service;

public interface IShortUrlService {

    /**
     * 输入长连接,返回短连接
     * @param longUrl 输入长连接
     * @return 返回短连接
     */
    String postLongUrl(String longUrl) throws InterruptedException;

    /**
     * 输入短连接,返回长连接
     * @param shortUrl 短连接
     * @return 长连接
     */
    String getLongUrl(Long shortUrl);
}
