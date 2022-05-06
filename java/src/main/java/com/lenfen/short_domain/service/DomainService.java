package com.lenfen.short_domain.service;

import com.lenfen.short_domain.bean.ShortDomain;

/**
 * 域名服务接口
 */
public interface DomainService {
    /**
     * 对长域名进行编码
     *
     * @param fullUrl 长域名
     * @return 编码后的信息
     */
    ShortDomain encode(String fullUrl);

    /**
     * 对长域名进行编码
     *
     * @param shortUrl 短域名
     * @return 解码后的信息
     */
    ShortDomain decode(String shortUrl);
}
