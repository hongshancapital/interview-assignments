package com.bingl.web.persist;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class ShortUrlEntity {
    private String shortUrl;
    private List<Integer> charIntList;
    private String md5Url;
    private String longUrl;
    @JSONField(serialize=false)
    private Date createTime;


    public String getShortUrl() {
        return shortUrl;
    }

    public ShortUrlEntity setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
        return this;
    }

    public List<Integer> getCharIntList() {
        return charIntList;
    }

    public ShortUrlEntity setCharIntList(List<Integer> charIntList) {
        this.charIntList = charIntList;
        return this;
    }

    public String getMd5Url() {
        return md5Url;
    }

    public ShortUrlEntity setMd5Url(String md5Url) {
        this.md5Url = md5Url;
        return this;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public ShortUrlEntity setLongUrl(String longUrl) {
        this.longUrl = longUrl;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ShortUrlEntity setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
