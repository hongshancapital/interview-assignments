package com.zoujing.shortlink.service;

public interface LinkGeneratorService {

    /**
     * 长链接获取短连接
     *
     * @param sourceApp 请求来源系统编号
     * @param longLink
     * @return String
     */
    String getShortLink(long sourceApp, String longLink);

    /**
     * 短链接获取长连接
     *
     * @param sourceApp 请求来源系统编号
     * @param shortLink
     * @return String
     */
    String getLongLink(long sourceApp, String shortLink);
}
