package com.david.urlconverter.service.web;


public interface IUrlConverterService {

    /**
     * 生成短域名
     * @param longUrl
     * @return
     */
    public String generateShortUrl(String longUrl);

    /**
     * 查询长域名
     * @param shortUrl
     * @return
     */
    public String retrieveLongUrl(String shortUrl);

}
