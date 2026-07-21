package com.rad.shortdomainname.service;

public interface DomainDataHolderService {
    String getLongUrl(String shortUrl);

    boolean putLongUrl(String shortUrl, String longUrl);
}
