package com.redwood.shorturl.service.inter;

/**
 * 短链服务层定义：
 *  1. 利用长链接生成短连接并存储。
 *  2. 利用短连接查询长链接并返回。
 * @Author Jack-ZG
 * @Date 2022-01-02
 */
public interface ShortUrlService {
    /**
     * 利用短链接查询长链接并返回
     * @param shortUrl
     * @return mainUrl String
     */
    String exchange(String shortUrl);

    /**
     * 利用长链接生成短链接并存储
     * @param mainUrl
     * @return shortUrl String
     */
    String generate(String mainUrl);
}
