package com.example.scdt.service;

import com.example.scdt.exception.BusinessException;

/**
 * @author JonathanCheung
 * @date 2021/12/11 17:30
 * @describe
 */
public interface IShortUrlTransferService {

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longUrl 长域名
     * @return 短域名
     */
    String longUrlToShortUrl(String longUrl);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     *
     * @param shortUrl 短域名
     * @return 长域名
     */
    String shortUrlToLongUrl(String shortUrl) throws BusinessException;
}
