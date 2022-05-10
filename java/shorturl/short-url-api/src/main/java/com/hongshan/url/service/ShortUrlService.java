package com.hongshan.url.service;

/**
 * @author lilejun
 * 短连接服务
 */
public interface ShortUrlService {
    /**
     * @param commonUrl 正常域名
     * @return 返回生成的短域名
     */
    String getShortUrl(String commonUrl, Integer length);


    /**
     * @param shortUrl 短域名
     * @return 长域名
     */
    String getCommonUrl(String shortUrl);

}
