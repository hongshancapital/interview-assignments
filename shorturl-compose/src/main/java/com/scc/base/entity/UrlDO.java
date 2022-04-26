package com.scc.base.entity;

/**
 * @author renyunyi
 * @date 2022/4/24 10:58 PM
 * @description table url
 **/
public class UrlDO {

    /**
     * auto increment id
     */
    private Long id;

    /**
     * short url
     */
    private String shortUrl;

    /**
     * long url
     */
    private String longUrl;

    //more field to  add

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
