package com.duoji.shortlink.domain;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月17日 19:34:00
 */
public interface ShortLinkHandler {
    /**
     * 生成短链接
     * @param longLink 长连接地址
     * @return 短链接字符串
     */
    String generateShortLink(String longLink);

    /**
     * 获取短链接对应的长连接
     * @param shortLink 短链接
     * @return 长连接
     */
    String findLongLink(String shortLink);
}
