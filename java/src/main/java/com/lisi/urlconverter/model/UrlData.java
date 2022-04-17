package com.lisi.urlconverter.model;

/**
 * @description: 存储短域名及其过期时间
 * @author: li si
 */
public class UrlData {
    /**
     * 短域名值
     **/
    private String val;

    /**
     * 该域名过期时间
     **/
    private long ttl;

    public UrlData(String val, long expireTime) {
        this.val = val;
        this.ttl = System.currentTimeMillis() + expireTime;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
