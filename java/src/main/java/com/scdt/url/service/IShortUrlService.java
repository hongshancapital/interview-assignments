package com.scdt.url.service;

public interface IShortUrlService {
    public String shortUrlCreate(String url);
    public String longUrlFind(String url);
}
