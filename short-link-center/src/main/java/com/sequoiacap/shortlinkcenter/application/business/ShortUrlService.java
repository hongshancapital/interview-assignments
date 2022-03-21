package com.sequoiacap.shortlinkcenter.application.business;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
public interface ShortUrlService {

    /**
     * 根据源url查询短链
     * @param sourceUrl 源url
     * @param targetDomainName 目标短域名
     * @return 短链
     */
    String getBySourceUrl(String sourceUrl, String targetDomainName);

    /**
     * 跟俊短链查询源url
     * @param shortUrl 短链
     * @return 源url
     */
    String getByShortUrl(String shortUrl);
}
