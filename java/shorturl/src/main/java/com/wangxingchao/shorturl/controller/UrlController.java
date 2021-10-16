package com.wangxingchao.shorturl.controller;

import com.wangxingchao.shorturl.service.UrlService;
import com.wangxingchao.shorturl.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiOperation("短域名转长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名信息（域名部分）",
            dataType = "String", paramType ="query", required = true, defaultValue = "Xa123k00")
    @RequestMapping("short2long")
    public Result short2long(@RequestParam String shortUrl) {
        return urlService.short2long(shortUrl);
    }

    /**
     * 接受长域名信息，返回短域名信息。
     * @param longUrl 长域名信息
     * @return 短域名信息
     */
    @ApiOperation("长域名转短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名信息（域名部分）",
            dataType = "String", paramType ="query", required = true, defaultValue = "google")
    @RequestMapping("long2short")
    public Result long2short(@RequestParam String longUrl) {
        return urlService.long2short(longUrl);
    }
}
