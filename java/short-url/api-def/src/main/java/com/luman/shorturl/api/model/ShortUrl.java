package com.luman.shorturl.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
public class ShortUrl implements Serializable {
    private long id;
    private String url;
    private String code;
    private Date expire;
    private Date createTime;
    @JsonIgnore
    public Long getExpireDays(){
        if(expire==null){
            return -1L;
        }
        return TimeUnit.MILLISECONDS.toDays(expire.getTime())-TimeUnit.MICROSECONDS.toDays(System.currentTimeMillis());
    }
    @JsonIgnore
    public int getExpireTime(){
        if(expire==null){
            return -1;
        }
        return (int)TimeUnit.MILLISECONDS.toDays(expire.getTime());
    }
}
