package com.sequoia.shorturl.service;

import com.sequoia.shorturl.common.SequoiaResponse;

/***
 *
 *@ DESC  TODO
 *
 *@Author qq
 *
 *@Date 2021/6/27 16:18
 *
 *@version v1.0
 *
 */
public interface ShortUrlService {
    /**
     * 根据原地址返回短地址
     * @param originalUrl
     * @return
     */
    public SequoiaResponse getShortUrl(String originalUrl);
    /**
     * 根据短地址返回原地址
     * @param originalUrl
     * @return
     */
    public SequoiaResponse getOriginalUrl(String shortUrl);

}
