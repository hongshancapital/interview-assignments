package com.wangxingchao.shorturl.service;

public interface UrlService {

    // 短域名转长域名
    String short2long(String shortUrl);

    // 长域名转短域名
    String long2short(String longUrl);
}
