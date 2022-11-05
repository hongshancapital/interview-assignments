package com.example.demo.service;

public interface ShortUrlService {
    /**
     * 根据长链接地址返回短链接地址
     * @param longURL
     * @return
     */
    String storeShortUrl(String longURL);

    /**
     * 根据短链接地址返回长链接地址
     * @param shortURL
     * @return
     */
    String getLongUrl(String shortURL);
}
