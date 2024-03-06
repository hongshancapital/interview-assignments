package com.zmc.shorturl.service;

import java.util.concurrent.ExecutionException;

/**
 * Description: 短链接接口
 * Author: Zmc
 * Date: 2021-12-11 18:20
 **/
public interface ShortUrlService {

    /**
     * 生成短域名
     *
     *
     * @param longUrl 长url
     * @return 短域名
     */
    String shortUrl(String longUrl) throws ExecutionException;

    /**
     * 获取长url映射
     *
     * @param shortUrl 短链接
     * @return 长链接
     */
    String parseLongUrl(String shortUrl);
}
