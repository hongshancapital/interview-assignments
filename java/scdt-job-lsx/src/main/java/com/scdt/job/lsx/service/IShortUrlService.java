package com.scdt.job.lsx.service;

/**
 * @author lsx
 */
public interface IShortUrlService {
    /**
     * 长域名映射为短域名
     * @param url
     * @return
     */
    String nativeUrlToShort(String url);

    /**
     * 通过短域名查询长域名
     * @param url
     * @return
     */
    String shortUrlToNative(String url);
}
