package com.service;

/**
 * @Author jeffrey
 * @Date 2021/10/11
 * @description: 短链接口
 */
public interface DomainNameService {
    /**
     * 根据长链接取短地址
     * @param longUrl
     * @return
     */
    String getShortUrl(String longUrl);

    /**
     * 根据短地址取长链接
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}
