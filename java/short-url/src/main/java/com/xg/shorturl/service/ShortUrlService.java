package com.xg.shorturl.service;

/**
 * 短链接服务类
 * @author xionggen
 */
public interface ShortUrlService {

    /**
     * 查询或者生成短链接：先查询，查询不到则生成并保存短链接
     * @param originalUrl 原始链接
     * @return 短连接
     */
    String queryOrGenerateShortUrl(String originalUrl);

    /**
     * 根据短链接查询对应原始链接
     * @param shortUrl 短链接
     * @return 原始链接
     */
    String queryOriginalUrl(String shortUrl);
}
