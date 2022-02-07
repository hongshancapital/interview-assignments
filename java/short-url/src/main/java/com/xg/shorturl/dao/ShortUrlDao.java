package com.xg.shorturl.dao;

/**
 * 短链接数据访问层
 * @author xionggen
 */
public interface ShortUrlDao {

    /**
     * 根据短链接查询原始连接
     * @param shortUrl 短连接
     * @return 原始连接
     */
    String queryOriginalUrl(String shortUrl);

    /**
     * 根据原始链接查询短链接
     * @param originalUrl 原始链接
     * @return 短链接
     */
    String queryShortUrl(String originalUrl);

    /**
     * 保存短链接与原始链接
     * @param shortUrl 短链接
     * @param originalUrl 原始链接
     * @return 1-成功，0-失败
     */
    int save(String shortUrl, String originalUrl);
}
