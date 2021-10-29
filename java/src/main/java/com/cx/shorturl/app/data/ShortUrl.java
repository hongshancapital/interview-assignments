package com.cx.shorturl.app.data;

public class ShortUrl {

    private String shortUrl;

    private String longUrl;

    public ShortUrl() {
    }

    public ShortUrl(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }


    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String tinyUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
