package com.hs.shortlink.service;

import com.github.benmanes.caffeine.cache.Cache;

public interface UrlTransformService {

    /**
     * 长连接生成短链接
     * @param longUrl 长连接
     * @param length 生成短链接指定位数
     * @return 短链接
     */
    String  getShortUrl(String longUrl, Integer length);

    /**
     * 短链接生成长连接
     * @param shortUrl 短连接
     * @return 生成长连接
     */
    String getLongUrl(String shortUrl);

    /**
     * 获取所有长短链映射关系
     * @return 所有映射关系
     */
    Cache<String, String> getCache();

}
