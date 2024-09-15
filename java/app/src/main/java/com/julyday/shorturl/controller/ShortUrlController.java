package com.julyday.shorturl.controller;

import com.julyday.shorturl.service.ShortUrlConverter;
import com.julyday.shorturl.utils.HttpUtils;
import com.julyday.shorturl.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
@RequestMapping(value = "/api/url", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation("长域名与短域名相互转换接口")
@Api(value = "长域名与短域名相互转换接口", protocols = "http", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShortUrlController {

    public static final String DEFAULT_PREFIX = "http://c.j.cn/";

    @Value("${shortUrl.prefix:" + DEFAULT_PREFIX + "}")
    private String prefix;

    @Autowired
    private ShortUrlConverter shortUrlConverter;

    @PostMapping("/short")
    @ApiOperation("长域名获取短域名")
    @ApiResponse(description = "长域名转换短域名", responseCode = "200")
    public Result<String> convert2ShortUrl(@ApiParam("长域名") @RequestParam String longUrl) {
        if (!HttpUtils.verifyUrl(longUrl)) {
            throw new InvalidParameterException("longUrl:" + longUrl + " is invalid");
        }
        String shortUrl = shortUrlConverter.generatorShortUrl(longUrl);
        shortUrl = prefix + shortUrl;
        return Result.newResult(shortUrl);
    }


    @GetMapping("/long")
    @ApiOperation(value = "通过短域名获取长域名")
    @ApiResponse(description = "返回长域名", responseCode = "200")
    public Result<String> getLongUrl(@ApiParam("短域名") @RequestParam String shortUrl) {
        if (shortUrl.startsWith(prefix)) {
            shortUrl = shortUrl.replace(prefix, "");
        }
        String longUrl = shortUrlConverter.getLongUrl(shortUrl);
        return Result.newResult(longUrl);
    }
}
