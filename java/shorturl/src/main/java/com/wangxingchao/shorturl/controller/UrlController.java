package com.wangxingchao.shorturl.controller;

import com.wangxingchao.shorturl.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "链接处理")
@RestController
@RequestMapping("url")
public class UrlController {

    @Resource
    private UrlService urlService;

    /**
     * 接受短域名信息，返回长域名信息。
     * @param shortUrl 短域名信息
     * @return 长域名信息
     */
    @ApiOperation("短链接转长链接")
    @RequestMapping("short2long")
    public String short2long(String shortUrl) {
        return urlService.short2long(shortUrl);
    }

    /**
     * 接受长域名信息，返回短域名信息。
     * @param longUrl 长域名信息
     * @return 短域名信息
     */
    @ApiOperation("长链接转短链接")
    @RequestMapping("long2short")
    public String long2short(String longUrl) {
        return urlService.long2short(longUrl);
    }
}
