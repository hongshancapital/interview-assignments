package com.scdt.interview.url.service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: lijin
 * @date: 2021年10月09日
 */
public interface UrlService {
    String storeLongUrl(String longUrl);

    String getLongUrl(String shortUrl);

    String generateShortUrl(String longUrl);

    AtomicLong getId();
}
