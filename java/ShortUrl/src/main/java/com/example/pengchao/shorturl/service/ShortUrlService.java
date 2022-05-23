package com.example.pengchao.shorturl.service;

/**
 * 短url的服务类
 *
 * @author pengchao04
 * @date 2022/5/23 10:56
 */
public interface ShortUrlService {

    /**
     * 通过 短url 获取 长url
     *
     * @param shortUrl 短url
     * @return 长url
     */
    String getLongUrl(String shortUrl);

    /**
     * 通过 长url，返回短url
     * @param longUrl
     * @return 短url
     */
    String getShortUrl(String longUrl);
}
