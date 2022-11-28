package com.jinblog.shorturl.service;


public interface Storage {
    public void save(String url, String shortUrl);
    public String find(String shortUrl);
    public void delete(String shortUrl);
}
