package com.hongshan.shorturl.model.url;

import lombok.Data;

/**
 * @author: huachengqiang
 * @date: 2022/1/16
 * @description:
 */
@Data
public class ShortUrl {
    /**
     * 短url
     */
    private String shortUrl;
    /**
     * 原始url
     */
    private String originUrl;
    /**
     * 过期时间，单位：秒
     */
    private Long expireAt;
}
