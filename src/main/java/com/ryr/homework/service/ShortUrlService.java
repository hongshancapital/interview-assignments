package com.ryr.homework.service;

public interface ShortUrlService {

    String getLongUrlByShortUrl(String shortUrl);

    String getShortUrlByLongUrl(String longUrl);
}
