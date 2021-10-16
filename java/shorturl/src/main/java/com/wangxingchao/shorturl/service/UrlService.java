package com.wangxingchao.shorturl.service;

import com.wangxingchao.shorturl.utils.Result;

public interface UrlService {

    // 短域名转长域名
    Result short2long(String shortUrl);

    // 长域名转短域名
    Result long2short(String longUrl);
}
