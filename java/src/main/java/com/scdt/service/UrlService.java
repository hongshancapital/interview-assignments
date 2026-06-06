package com.scdt.service;

/**
 * UrlService
 *
 * @Author: lenovo
 * @since: 2021-12-16
 */
public interface UrlService {
    /**
     * 根据短url获取完整的url并返回
     * @param shortUrl 短url
     * @return 返回长url
     */
    String get(String shortUrl);

    /**
     * 将长url转换为短url返回
     * @param longUrl 长url
     * @return 返回短url
     */
    String put(String longUrl);
}
