package com.wh.franken.shorturl.app.controller;


import com.wh.franken.shorturl.app.exceptions.InvalidParameterException;
import com.wh.franken.shorturl.app.service.ShortUrlService;
import com.wh.franken.shorturl.app.utils.HttpUtils;
import com.wh.franken.shorturl.app.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author fanliang
 */
@RestController
@RequestMapping("/api/shortUrl")
@ApiOperation("长域名、短域名相互转换接口")
@Api(value = "长域名、短域名相互转换接口", protocols = "application/json", produces = "application/json")
@RequiredArgsConstructor
public class ShortUrlController {

    @Value("${default.domain}")
    public static final String DEFAULT_DOMAIN = "";

    private final ShortUrlService shortUrlService;

    @PostMapping("/convert2ShortUrl")
    @ApiOperation("长域名获取短域名")
    @ApiResponse(description = "返回转换后的短域名", responseCode = "200")
    public Result<String> convert2ShortUrl(@ApiParam("需要转换的长域名") @RequestParam String longUrl) throws ExecutionException {
        if (!HttpUtils.verifyUrl(longUrl)) {
            throw new InvalidParameterException("longUrl:" + longUrl + " is invalid");
        }
        String shortUrl = shortUrlService.generatorShortUrl(longUrl);
        shortUrl = DEFAULT_DOMAIN + shortUrl;
        return Result.newResult(shortUrl);
    }


    @GetMapping("/parseLongUrl")
    @ApiOperation(value = "通过短域名获取长域名", protocols = "application/json")
    @ApiResponse(description = "返回长域名", responseCode = "200")
    public Result<String> parseLongUrl(@ApiParam("短域名") @RequestParam String shortUrl) {
        shortUrl = shortUrl.replace(DEFAULT_DOMAIN, "");
        String longUrl = shortUrlService.parseLongUrl(shortUrl);
        return Result.newResult(longUrl);
    }
}
