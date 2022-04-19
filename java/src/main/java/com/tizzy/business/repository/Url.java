package com.tizzy.business.repository;

public class Url {

    private String longUrl;

    private long expiresDate;

    public Url(){

    }

    public Url(String longUrl, long expiresDate) {
        this.longUrl = longUrl;
        this.expiresDate = expiresDate;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public long getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(long expiresDate) {
        this.expiresDate = expiresDate;
    }
}
