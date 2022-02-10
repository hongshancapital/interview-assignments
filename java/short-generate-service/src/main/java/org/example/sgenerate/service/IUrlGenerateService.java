package org.example.sgenerate.service;

import org.example.sgenerate.model.UrlMappingInfo;

import java.util.Date;

/**
 * 链接生成接口
 *
 * @author liuyadu
 */
public interface IUrlGenerateService {

    /**
     * 创建短链接
     *
     * @param originalUrl 原始链接
     * @param expiryTime  过期时间
     * @return
     */
    UrlMappingInfo generateShortUrl(String originalUrl, Date expiryTime);


    /**
     * 读取短链接信息
     * @param shortUrl
     * @return
     */
    UrlMappingInfo readShortUrl(String shortUrl);
}
