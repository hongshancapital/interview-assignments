package com.zhangliang.suril.service;

/**
 * 短网址服务
 *
 * @author zhang
 * @date 2021/12/02
 */
public interface ShortUrlService {

    /**
     * 保存url
     *
     * @param url url
     * @return {@link String}
     */
    String saveUrl(String url);

    /**
     * 获取url
     *
     * @param url url
     * @return {@link String}
     */
    String getUrl(String url);
}
