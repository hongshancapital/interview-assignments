package com.wenchao.jacoco.demo.service;

import java.util.Optional;

/**
 * 域名转换服务接口
 *
 * @author Wenchao Gong
 * @date 2021/12/15 15:52
 */
public interface UrlService {

    /**
     * 长域名转短域名
     *
     * @param longUrl 长域名
     * @return 短域名
     */
    String getShortUrl(String longUrl);

    /**
     * 短域名转长域名
     *
     * @param shortUrl 短域名
     * @return 长域名
     */
    Optional<String> getLongUrl(String shortUrl);
}
