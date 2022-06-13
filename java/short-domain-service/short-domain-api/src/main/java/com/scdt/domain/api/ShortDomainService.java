package com.scdt.domain.api;

import com.scdt.domain.api.common.Response;

/**
 * 短域名服务
 *
 * @author tuxiaozhou
 * @date 2022/4/2
 */
public interface ShortDomainService {

    /**
     * 短域名存储接口
     *
     * @param longUrl 长网址
     * @return 短网址
     */
    Response<String> createShortUrl(String longUrl);

    /**
     * 短域名读取接口
     *
     * @param shortUrl 短网址
     * @return 长网址
     */
    Response<String> getLongUrl(String shortUrl);

}
