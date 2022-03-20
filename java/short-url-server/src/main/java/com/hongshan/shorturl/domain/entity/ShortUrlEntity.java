package com.hongshan.shorturl.domain.entity;

import lombok.Data;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 */
@Data
public class ShortUrlEntity {
    /**
     * 短链接后缀
     */
    private String shortKey;
    /**
     * 原始链接
     */
    private String originUrl;
    /**
     * 过期时间（截止时间）单位: 毫秒(ms)
     */
    private Long expireAt;
    /**
     * 原始链接的hash（base64字符串）
     */
    private String hash;
}
