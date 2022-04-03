package com.findme.url.controller;

import com.findme.url.service.TransformUrl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformController {
    @Autowired
    TransformUrl transformUrl;



    @GetMapping("getShortUrl")
    @ApiOperation(value = "获取短域名url", notes = "短域名存储接口：接受长域名信息，返回短域名信息")
    public String longTransformShortUrl(String longUrl) {
        return transformUrl.longTransformShortUrl(longUrl);
    }

    @GetMapping("getLongUrl")
    @ApiOperation(value = "获取长域名url", notes = "短域名读取接口：接受短域名信息，返回长域名信息")
    public String shortTransformLongUrl(String shortUrl) {
        return transformUrl.shortTransformLongUrl(shortUrl);
    }


}
