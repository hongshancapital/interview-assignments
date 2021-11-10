package com.nbasoccer.shorturl.service;

public interface IShortUrlService {

    String convertShortUrl(String originUrl);

    String convertLongUrl(String shortUrl);
}
