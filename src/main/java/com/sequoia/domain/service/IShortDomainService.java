package com.sequoia.domain.service;

public interface IShortDomainService {
    /**
     * 长域名转化为短域名
     *
     * @param url base编码的长域名
     * @return 短域名
     */
    String toShortUrl(String url);

    /**
     * 短域名恢复成长域名
     *
     * @param url base编码的短域名
     * @return 长域名
     */
    String restoreToOriginUrl(String url);
}
