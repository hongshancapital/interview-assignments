package com.scdt.shortlink.client.service;

import com.scdt.shortlink.client.dto.Result;

/**
 * 长短链互转service接口
 *
 * @Author tzf
 * @Date 2022/4/28
 */
public interface LinksService {
    /**
     * 长链转短链
     *
     * @param url 长链
     * @return 短链
     */
    Result<String> getShortLink(String url);

    /**
     * 短链兑换长链
     *
     * @param shortLink
     * @return
     */
    Result<String> getOriginalLink(String shortLink);
}
