package com.scdt.china.url.mapping.service;

public interface IUrlMappingService {

    /**
     * 获取短链接
     * @param originalUrl
     * @return
     */
    String getShortUrl(String originalUrl);

    /**
     * 获取长链接
     * @param shortUrl
     * @return
     */
    String getOrinigalUrl(String shortUrl);

}
