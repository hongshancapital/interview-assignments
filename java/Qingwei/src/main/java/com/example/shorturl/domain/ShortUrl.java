/*
 * Copyright (C) 2021 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.domain;

/**
 * 实体类
 * com.example.shorturl.domain shorturl
 *
 * @create mencius 2021-02-18 19:34
 */
public class ShortUrl {

    Long id;
    String longUrl;
    String shortUrl;

    public ShortUrl() {
    }

    public ShortUrl(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
