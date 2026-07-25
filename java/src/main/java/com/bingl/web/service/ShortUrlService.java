package com.bingl.web.service;


public interface ShortUrlService {
    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     */
    public String  saveShortUrl(String longUrl);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * @param shortUrl
     * @return
     */
    public String readLongUrl(String shortUrl) throws Exception;
}


