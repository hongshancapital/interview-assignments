package com.interview.wph.shorturl.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;


@TableName("t_short_url")
@AllArgsConstructor
public class ShortUrlEntity {
    // hash 索引，不涉及范围查找
    @TableId(type= IdType.ASSIGN_ID)
    private Long shortUrlId;
    private Integer longUrlHash;
    private String longUrl;

    public Long getShortUrlId() {
        return shortUrlId;
    }

    public void setShortUrlId(Long shortUrlId) {
        this.shortUrlId = shortUrlId;
    }

    public Integer getLongUrlHash() {
        return longUrlHash;
    }

    public void setLongUrlHash(Integer longUrlHash) {
        this.longUrlHash = longUrlHash;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
