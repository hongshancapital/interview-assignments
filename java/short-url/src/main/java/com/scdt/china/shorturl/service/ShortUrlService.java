package com.scdt.china.shorturl.service;

/**
 * 短域名service接口
 *
 * @author：costa
 * @date：Created in 2022/4/12 11:10
 */
public interface ShortUrlService {
    String getShortUrl(String url);

    String getUrl(String code);
}
