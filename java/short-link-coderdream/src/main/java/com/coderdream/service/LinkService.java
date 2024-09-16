package com.coderdream.service;

/**
 * 短链接服务
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
public interface LinkService {
    /**
     * 通过长链接生成短链接
     *
     * @param longLink 长链接
     * @return 短链接
     */
    String getShortLink(String longLink);

    /**
     * 通过短链接查找长链接
     *
     * @param shortLink 短链接
     * @return 长链接
     */
    String getLongLink(String shortLink);
}
