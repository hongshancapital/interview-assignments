package com.sequoiacap.sdn.service;

/**
 * @author : fanzhaofei
 * @date : 2021/5/4 21:30
 */
public interface DomainService {
    /**
     * 通过长域名转换短域名,存储并返回
     * @param longDomain 长域名
     * @return 短域名
     */
    String langToShort(String longDomain);
    /**
     * 通过短域名获取长域名
     * @param shortDomain 长域名
     * @return 长域名
     */
    String getLang(String shortDomain);

}
