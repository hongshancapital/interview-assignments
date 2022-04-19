package com.mortimer.shortenurl.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "接口请求响应体")
@Getter
public class UrlMapping {
    @Schema(description = "短域名地址")
    private String shortUrl;
    @Schema(description = "长域名地址")
    private String longUrl;

    public UrlMapping(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }
}
