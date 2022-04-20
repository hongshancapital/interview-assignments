package com.link.service;

/**
 * 长短域名转换接口
 *
 * @auth zong_hai@126.com
 * @date 2022-04-15
 * @desc
 */

public interface LinkService {

    /**
     * 长域名映射为短域名
     *
     * @param longLink 长域名
     * @return 短域名
     */
    String generateShortLinkByLongLink(String longLink);

    /**
     * 短域名映射长域名
     *
     * @param shortLink 短域名
     * @return 长域名
     */
    String queryLongLinkByShortLink(String shortLink);
}
