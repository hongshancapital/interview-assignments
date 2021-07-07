package com.shorturl.service;

public interface IUrlService {
    /**
     * 根据长链接生成短链接
     *
     * @param url 长链接
     * @return 短链接
     */
    String createShortUrl(String url);

}
