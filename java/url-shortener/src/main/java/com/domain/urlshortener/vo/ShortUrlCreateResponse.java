package com.domain.urlshortener.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 20:11
 */
@Schema(description = "短链创建响应体")
@Getter
public class ShortUrlCreateResponse {

    @Schema(description = "原始长链URL")
    private String longUrl;
    @Schema(description = "生成的短链URL")
    private String shortUrl;

    public ShortUrlCreateResponse(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

}
