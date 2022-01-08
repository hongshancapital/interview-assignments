package com.tizzy.business.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UrlLongRequest {

    @NotNull(message = "longUrl不能为空")
    @Size(max = 1024, min = 9, message = "longUrl长度必须大于8, longUrl长度必须小于等于1024")
    private String longUrl;

    private long expiresTime;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }
}
