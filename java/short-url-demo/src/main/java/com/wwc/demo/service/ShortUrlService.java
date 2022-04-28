package com.wwc.demo.service;

import com.wwc.demo.common.ResponseResult;

public interface ShortUrlService {
    /**
     * 根据长链接生成短连接
     * @param longUrl
     * @return
     */
    ResponseResult getShortUrl(String longUrl);

    /**
     * 根据短码获取长链接
     * @param shortCode
     * @return
     */
    ResponseResult getLongUrl(String shortCode);
}
