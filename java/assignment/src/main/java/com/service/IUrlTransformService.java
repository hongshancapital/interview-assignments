package com.service;

public interface IUrlTransformService {
    String getShortUrl(String longUrl);
    String getLongUrl(String shortUrl);
}
