package com.polly.shorturl.entity;

import java.io.Serializable;

/**
 * @author polly
 * @date 2022.03.20 11:01:00
 */
public class ShortUrl implements Serializable {
    private static final long serialVersionUID = -3368542116687838153L;
    private Long id;
    private String url;

    public ShortUrl() {
    }

    public ShortUrl(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public ShortUrl setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ShortUrl setUrl(String url) {
        this.url = url;
        return this;
    }
}
