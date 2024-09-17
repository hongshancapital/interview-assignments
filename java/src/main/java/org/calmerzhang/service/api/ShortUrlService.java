package org.calmerzhang.service.api;

import org.calmerzhang.common.exception.BusinessException;

/**
 * 短域名服务
 *
 * @author calmerZhang
 * @create 2022/01/06 8:45 下午
 */
public interface ShortUrlService {

    /**
     * 根据长域名获取短域名
     * @param longUrl
     * @return
     */
    String getShortUrl(String longUrl) throws BusinessException;

    /**
     * 根据短域名获取长域名
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}
