package com.url.service;

import com.url.bean.UrlResultBean;

/**
 * 域名转换接口服务
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
public interface UrlTransService {

    /**
     * 根据long url获取short url
     * @param longUrl
     * @return
     */
    public UrlResultBean getShortUrl(String longUrl);

    /**
     * 根据short url获取原url
     * @param shortUrl
     * @return
     */
    public UrlResultBean getLongUrl(String shortUrl);

}
