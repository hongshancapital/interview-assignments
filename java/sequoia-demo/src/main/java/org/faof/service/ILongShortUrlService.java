package org.faof.service;

public interface ILongShortUrlService {

    String getLong2ShortUrl(String longUrl);

    String getShort2LongUrl(String shortUrl);

    void initService();

}
