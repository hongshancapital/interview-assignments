package com.david.urlconverter.service.web;

/**
 * web服务缓存短域名到长域名的映射
 */
public interface IShortUrlLocalCacheService {

    /**
     * 保存短域名到长域名的映射
     * @param shortUrl
     * @param longUrl
     * @return
     */
    public void storeShortToLongMapping(String shortUrl, String longUrl);

    /**
     * 根据短域名查询长域名
     * @param shortUrl
     * @return
     */
    public String queryLongUrlInCache(String shortUrl);
}
