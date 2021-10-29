package com.cx.shorturl.app.controller;


import com.cx.shorturl.app.service.ShortUrlService;
import com.cx.shorturl.app.utils.HttpUtils;
import com.cx.shorturl.app.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shortUrl")
@ApiOperation("长短域名转换接口")
@Api(value = "长域名、短域名相互转换接口", protocols = "application/json", produces = "application/json")
public class ShortUrlController {

    public static final String DEFAULT_TINY_DOMAIN = "http://c.t.cn/";

    @Value("${tinyUrl.shortDomain:" + DEFAULT_TINY_DOMAIN + "}")
    private String shortDomain = DEFAULT_TINY_DOMAIN;

    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping("/convert2ShortUrl")
    @ApiOperation("长域名获取短域名")
    @ApiResponse(description = "返回转换后的短域名", responseCode = "200")
    public Result convert2ShortUrl(@ApiParam("需要转换的长域名") @RequestParam String longUrl) {
        if (!HttpUtils.verifyUrl(longUrl)) {
            return Result.invalidResult("longUrl:" + longUrl + " is invalid");
        }
        String shortUrl = shortUrlService.generatorShortUrl(longUrl);
        shortUrl = shortDomain + shortUrl;
        return Result.newResult(shortUrl);
    }


    @GetMapping("/getLongUrl")
    @ApiOperation(value = "通过短域名获取长域名", protocols = "application/json")
    @ApiResponse(description = "返回长域名", responseCode = "200")
    public Result getLongUrl(@ApiParam("短域名") @RequestParam String shortUrl) {
        if (shortUrl.startsWith(shortDomain)) {
            shortUrl = shortUrl.replace(shortDomain, "");
        }
        String longUrl = shortUrlService.getLongUrl(shortUrl);
        return Result.newResult(longUrl);
    }
}
