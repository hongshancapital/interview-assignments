package com.zc.shorturl.service;

public interface ShortUrlService {

    /**
     * 创建短链接
     * @param longUrl
     * @return shortUrl
     */
    String createShortUrl(String longUrl);

    /**
     * 根据短链接返回长链接
     * @param shortUrl
     * @return longUrl
     */
//    String getLongUrl(String shortUrl);

    /**
     * 根据shortUrl从缓存找longUrl
     * @param shortUrl
     * @return longUrl
     */
    String getLongUrlFromCache(String shortUrl);

    /**
     * 更新short2long缓存 k:shortUrl v:longUrl
     * @param shortUrl
     * @return  longUrl
     */
    String updateShort2Long(String shortUrl, String longUrl);
}
