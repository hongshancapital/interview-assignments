package com.domain.urlshortener.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 23:05
 */
@Schema(description = "短链读取响应体")
@Getter
public class ShortUrlGetResponse {

    @Schema(description = "原始长链URL")
    private String longUrl;

    @Schema(description = "请求的短链URL")
    private String shortUrl;

    public ShortUrlGetResponse(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

}
