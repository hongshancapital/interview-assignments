package com.adrian.service;

/**
 * 长短域名转换Service层接口
 */
public interface UrlService {

    /**
     * 长域名转换成短域名
     * @param url 完整的长域名
     * @return 短域名
     */
    String transformUrl(String url);

    /**
     * 通过短域名找到对应的长域名
     * @param url 短域名
     * @return 完整的长域名
     */
    String findOriginalUrl(String url);
}
