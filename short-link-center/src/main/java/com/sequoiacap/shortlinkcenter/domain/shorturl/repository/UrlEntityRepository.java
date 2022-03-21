package com.sequoiacap.shortlinkcenter.domain.shorturl.repository;

import com.sequoiacap.shortlinkcenter.domain.shorturl.entity.UrlEntity;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
public interface UrlEntityRepository {

    /**
     * 保存短链服务
     * @param urlEntity 短链服务
     */
    void saveShortUrl(UrlEntity urlEntity);

    /**
     * 根据源url查询
     * @param sourceUrl 源url
     * @return 短链
     */
    String getShortUrlBySourceUrl(String sourceUrl);

    /**
     * 根据短链接查询
     * @param shortUrl 短链url
     * @return 源链接
     */
    String getSourceUrlByShortUrl(String shortUrl);
}
