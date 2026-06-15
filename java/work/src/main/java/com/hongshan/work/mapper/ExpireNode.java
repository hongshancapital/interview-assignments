package com.hongshan.work.mapper;

/**
 * 底层存储的value对象
 */
public class ExpireNode {
    // 短域名
    private String shortUrl;
    // 过期时间
    private long expire;

    public ExpireNode(String shortUrl, long expire){
        this.shortUrl = shortUrl;
        this.expire = expire;
    }

    public void setShorUrl(String shortUrl){
        this.shortUrl = shortUrl;
    }

    public void setExpire(long expire){this.expire = expire;}

    public String getShorUrl(){
        return shortUrl;
    }

    public long getExpire(){
        return expire;
    }

}