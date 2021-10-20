package com.assignment.service;

import com.assignment.vo.Result;

/**
 * 短域名服务service
 *
 * @author shifeng
 */
public interface DomainService {

    /**
     * 获取短域名
     *
     * @param longUrl
     * @return
     */
    Result getShortUrl(String longUrl);

    /**
     * 查询长域名
     *
     * @param shortUrl
     * @return
     */
    Result getLongUrl(String shortUrl);
}
