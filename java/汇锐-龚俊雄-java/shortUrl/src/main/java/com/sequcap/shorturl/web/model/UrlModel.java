package com.sequcap.shorturl.web.model;

import java.io.Serializable;
import java.util.Date;

public class UrlModel implements Serializable {

    private static final long serialVersionUID = -8880636218812475965L;

    private String murmurCode;
    private String longUrl;
    private String shortUrl;
    private Date createdDate;

    public String getMurmurCode() {
        return murmurCode;
    }

    public void setMurmurCode(String murmurCode) {
        this.murmurCode = murmurCode;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
