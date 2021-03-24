package com.snail.shorturlservice.po;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.base.MoreObjects;

import java.util.Date;

@TableName("short_url")
public class ShortUrlPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String shortKey; //短链接key，不含域名，建唯一索引
    private String longUrl; //原始url
    private Long longHashCode; //原始url的哈希值
    private Integer seq; //原始url的哈希值冲突后，计算短链接key的计数序号
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Long getLongHashCode() {
        return longHashCode;
    }

    public void setLongHashCode(Long longHashCode) {
        this.longHashCode = longHashCode;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("shortKey", shortKey)
                .add("longUrl", longUrl)
                .add("longHashCode", longHashCode)
                .add("seq", seq)
                .add("createTime", createTime)
                .toString();
    }
}
