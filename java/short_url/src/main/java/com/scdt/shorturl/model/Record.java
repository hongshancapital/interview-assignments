package com.scdt.shorturl.model;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.Serializable;

@ApiResponse(description = "短域名与长域名的映射记录")
public class Record implements Serializable {
    private static final long serialVersionUID = -3187660533266059415L;

    private String shortUrl;
    private String longUrl;

    public Record() {
    }

    public Record(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Record setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
        return this;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public Record setLongUrl(String longUrl) {
        this.longUrl = longUrl;
        return this;
    }
}
