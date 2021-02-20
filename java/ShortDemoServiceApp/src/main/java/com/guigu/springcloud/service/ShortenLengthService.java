package com.guigu.springcloud.service;

public interface ShortenLengthService {

    public String transferShortUrl(String url,String suffix);
    public String getOriginalUrl(String dUrl);
}
