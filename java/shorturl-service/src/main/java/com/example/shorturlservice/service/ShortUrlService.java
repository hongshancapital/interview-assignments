package com.example.shorturlservice.service;

import com.example.shorturlservice.domain.BizException;

/**
 * @Description 短域名的存储和读取
 * @Author xingxing.yu
 * @Date 2022/04/15 17:49
 **/
public interface ShortUrlService {

    /**
     * 短域名存储：接受长域名信息，返回短域名信息
     *
     * @param longUrl
     * @return
     * @throws BizException
     */
    String saveLongUrl(String longUrl) throws BizException;

    /**
     * 长域名读取：接受短域名信息，返回长域名信息
     *
     * @param shortUrl
     * @return
     */
    String getShortUrl(String shortUrl);
}
