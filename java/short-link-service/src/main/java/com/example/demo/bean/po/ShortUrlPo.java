package com.example.demo.bean.po;

/**
 * @author shenbing
 * @since 2022/1/8
 */
public class ShortUrlPo {

    /**
     * 短连接 code
     */
    private String code;

    /**
     * 短连接 id
     */
    private Long id;

    /**
     * 原始地址
     */
    private String originalUrl;

    /**
     * 短连接
     */
    private String shortUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
