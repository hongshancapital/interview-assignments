package io.nigelwy.javaassignments.api;

import io.nigelwy.javaassignments.api.response.GenerateShorturlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "短链接服务接口")
public interface ShortUrlApi {

    @Operation(summary = "通过长链接生成短链接")
    @ApiResponse(
            responseCode = "201",
            description = "短域名生成成功"
    )
    GenerateShorturlResponse generateShortUrl(@Schema(example = "https://www.qq.com",
            type = "string",
            required = true,

            description = "原始的url长链接") String originUrl);

    @Operation(summary = "通过短链接返回长链接")
    @ApiResponse(
            responseCode = "302",
            description = "原始链接找到，重定向到原始链接"
    )
    @ApiResponse(
            responseCode = "404",
            description = "原始链接未找到或已失效"
    )
    ResponseEntity<Void> getOriginUrl(@Schema(example = "aufnsdt",
            type = "string",
            description = "生成的url短链接") String shortUrl);
}
