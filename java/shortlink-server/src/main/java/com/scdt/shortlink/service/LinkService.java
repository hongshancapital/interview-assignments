package com.scdt.shortlink.service;

/**
 * @author xbhong
 * @date 2022/4/17 16:07
 */
public interface LinkService {
    /**
     * 接受长域名信息，返回短域名信息
     *
     * @param longLink
     * @return
     */
    String createShortLink(String longLink);

    /**
     * 接受短域名信息，返回长域名信息
     *
     * @param shortLink
     * @return
     */
    String getLongLink(String shortLink);
}
