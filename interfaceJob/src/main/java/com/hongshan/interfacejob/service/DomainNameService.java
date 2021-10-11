package com.hongshan.interfacejob.service;

public interface DomainNameService {
    /**
     * 根据长域名取短域名
     */
    String getShortUrl(String longUrl);

    /**
     * 根据短域名取长域名
     */
    String getLongUrl(String shortUrl);
}
