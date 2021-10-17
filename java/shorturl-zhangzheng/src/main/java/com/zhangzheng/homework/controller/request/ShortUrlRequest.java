package com.zhangzheng.homework.controller.request;

import lombok.Data;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/9 下午1:47
 */
@Data
public class ShortUrlRequest {
    private String shortUrl;
    private String longUrl;
}
