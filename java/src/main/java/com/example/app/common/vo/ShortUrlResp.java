package com.example.app.common.vo;

/**
 * @author voidm
 * @date 2021/9/18
 */
public class ShortUrlResp {

    private String shortUrl;

    public ShortUrlResp(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}