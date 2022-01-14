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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (shortUrl != null ? !shortUrl.equals(record.shortUrl) : record.shortUrl != null) return false;
        return longUrl != null ? longUrl.equals(record.longUrl) : record.longUrl == null;
    }

    @Override
    public int hashCode() {
        int result = shortUrl != null ? shortUrl.hashCode() : 0;
        result = 31 * result + (longUrl != null ? longUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Record{" +
                "shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                '}';
    }
}
