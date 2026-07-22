package com.hongshan.shorturl.model.resps;

import lombok.Data;

/**
 * @author: huachengqiang
 * @date: 2022/1/16
 * @description:
 */
@Data
public class ShortUrlResp {
    /**
     * 短url
     */
    private String shortUrl;
    /**
     * 原始url
     */
    private String originUrl;
    /**
     * 有效期，单位：秒
     */
    private Long expireAt;
}
