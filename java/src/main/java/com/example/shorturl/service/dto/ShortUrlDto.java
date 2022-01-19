package com.example.shorturl.service.dto;

import lombok.Data;

/**
 * @author yyp
 * @date 2022/1/16 10:30
 */
@Data
public class ShortUrlDto {
    /**
     * 短链接
     */
    private String shortUrl;
    /**
     * 长链接
     */
    private String longUrl;
    /**
     * 失效时间
     * -1 表示永久；否则表示失效时间
     */
    private Long expireTime;
}
