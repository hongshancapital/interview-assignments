package com.scdt.aeolus.service;

import java.util.Optional;

public interface ShortUrlService {
    /**
     * 长域名转换为短域名
     * @param originalUrl
     * @return
     */
    String saveUrl(String originalUrl);

    /**
     * 短域名转换为长域名
     * @param url
     * @return
     */
    Optional<String> getUrl(String shortUrl);
}