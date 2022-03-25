package cn.sequoiacap.shorturl.service;

import cn.sequoiacap.shorturl.exception.StoreException;

public interface ShortUrlService {
    /**
     * 根据长链接生成短链接
     *
     * @param originalUrl 长链接
     * @return 短链接 id
     */
    String generate(String originalUrl) throws StoreException;

    /**
     * 根据短链接 id 获取长链接
     *
     * @param shortUrlId 短链接 id
     * @return 长链接
     */
    String get(String shortUrlId) throws StoreException;
}
