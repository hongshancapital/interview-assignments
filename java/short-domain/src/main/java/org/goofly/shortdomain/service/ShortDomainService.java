package org.goofly.shortdomain.service;

/**
 * @author : goofly
 * @Email: 709233178@qq.com
 */
public interface ShortDomainService {

    /**
     * 长域名转短域名
     * @param url
     * @return
     */
    String convertShortDomain(String url);

    /**
     * 短域名 转 长域名
     * @param shortCode
     * @return
     */
    String convertOriginalDomain(String shortCode);
}
