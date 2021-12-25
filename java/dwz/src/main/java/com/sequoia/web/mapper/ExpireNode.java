package com.sequoia.web.mapper;

public class ExpireNode {
    private String shortUrl;
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
