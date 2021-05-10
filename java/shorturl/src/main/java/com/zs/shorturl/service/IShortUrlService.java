package com.zs.shorturl.service;

import com.zs.shorturl.enity.vo.Result;

/**
 * @author zs
 * @date 2021/5/10
 */
public interface IShortUrlService {


    /**
     * 通过长连接获取短链接
     * @param longUrl
     * @return
     */
    Result getShortUrlFromLongUrl(String longUrl);


    /**
     * 通过短链接获取长连接
     * @param shortUrl
     * @return
     */
    Result getLongUrlFromShortUrl(String shortUrl);

}
