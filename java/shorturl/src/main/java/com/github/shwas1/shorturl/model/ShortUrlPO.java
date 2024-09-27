package com.github.shwas1.shorturl.model;

import java.util.Date;

/**
 * 短链接实体类
 */
public class ShortUrlPO {

    /**
     * 短路径
     */
    private String shortUrl;

    /**
     * 原始URL链接
     */
    private String originalUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
