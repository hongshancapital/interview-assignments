package com.interview.service;

/**
 * @author: nyacca
 * @Date: 2021/12/17 11:40
 */


public interface DomainService {

    /**
     * 将长域名变更为短域名
     * @param longUrl 长域名
     * @return 短域名
     */
    String transLongUrlToShortUrl(String longUrl);

    /**
     * 通过短域名获取长域名
     * @param shortUrl 短域名
     * @return 长域名
     */
    String getLongUrlByShortUrl(String shortUrl);


}
