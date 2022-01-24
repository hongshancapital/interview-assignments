package com.zh.shortUrl.service;

import com.zh.shortUrl.common.BaseResponse;

/**
 * @Author hang.zhang
 * @Date 2022/1/18 16:25
 * @Version 1.0
 */
public interface ShortUrlService {
    BaseResponse getShortUrl(String longUrl);

    BaseResponse getLongUrl(String shortUrl);

}
