package com.ryr.homework.controller;

import com.ryr.homework.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortUrl")
@Api(tags = "短域名服务")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation("输入长域名 获取短域名")
    @GetMapping("/getShortUrl")
    public String getShorUrl(@ApiParam(value = "长域名", required = true) @RequestParam("longUrl") String longUrl) {
        String shortUrl = shortUrlService.getShortUrlByLongUrl(longUrl);
        return shortUrl;
    }

    @ApiOperation("输入短域名 获取长域名")
    @GetMapping("/{shortUrl}")
    public String getLongUrl(@ApiParam(value = "短域名", required = true) @PathVariable("shortUrl") String shortUrl) {
        return shortUrlService.getLongUrlByShortUrl(shortUrl);
    }
}
