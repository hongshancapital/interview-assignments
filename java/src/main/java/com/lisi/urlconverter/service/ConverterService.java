package com.lisi.urlconverter.service;

/**
 * @description: 域名转换服务Service
 * @author: li si
 */
public interface ConverterService {
    /**
     * 长域名转为短域名
     * @param longUrl
     * @return
     */
    String convert(String longUrl);

    /**
     * 短域名转为长域名
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}
