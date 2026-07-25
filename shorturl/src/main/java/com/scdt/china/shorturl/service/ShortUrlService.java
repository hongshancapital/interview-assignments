package com.scdt.china.shorturl.service;

public interface ShortUrlService {

    String generateShortUrl(String url);

    String fetchUrl(String shortUrl);
}
