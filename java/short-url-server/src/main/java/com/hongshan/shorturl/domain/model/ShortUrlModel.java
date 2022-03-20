package com.hongshan.shorturl.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hongshan.shorturl.config.Constants;
import com.hongshan.shorturl.domain.entity.ShortUrlEntity;
import lombok.Data;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortUrlModel {
    /**
     * 短链接 eg: http://xx.cn/jksdSi9U
     */
    private String shortUrl;
    /**
     * 原始链接
     */
    private String originUrl;
    /**
     * 过期时间，精确到毫秒(ms)
     */
    private Long expireAt;

    public ShortUrlModel(ShortUrlEntity shortUrlEntity) {
        setShortUrl(Constants.PREFIX + shortUrlEntity.getShortKey());
        setOriginUrl(shortUrlEntity.getOriginUrl());
        setExpireAt(shortUrlEntity.getExpireAt());
    }
}
