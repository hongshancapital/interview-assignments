package com.yilong.shorturl.service;

public interface UrlService {

    String saveOriginUrl(String originUrl);

    String getOriginUrlByShort(String shortUrl);
}
