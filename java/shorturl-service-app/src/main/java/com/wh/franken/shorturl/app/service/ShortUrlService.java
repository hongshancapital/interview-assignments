package com.wh.franken.shorturl.app.service;

import java.util.concurrent.ExecutionException;

/**
 * @author fanliang
 */
public interface ShortUrlService {

    /**
     * 生成短域名方法，通过该函数可以生成短域名，此处只生成一个默认短域名，对于多渠道产生的短域名，并可能按照渠道统计的话，
     * 就需要生成多个短域名，只需将域名加上前缀区分即可，这里先不考虑这种情况
     *
     * @param longUrl 原始url
     * @return 短域名
     */
    String generatorShortUrl(String longUrl) throws ExecutionException;

    /**
     * 获取长url映射
     *
     * @param shortUrl 短链接
     * @return 长链接
     */
    String parseLongUrl(String shortUrl);
}
