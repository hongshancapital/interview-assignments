package com.mjl.api;

public interface ShortUrlService {

    String getShortUrlSuffix(String longUrl);

    String getLongUrlBySuffix(String shortUrlSuffix);

    String getLongUrl(String shortUrl);

    String generateShortUrl(String longUrl);
}
