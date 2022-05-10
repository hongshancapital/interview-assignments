package com.hongshan.controller;

import com.hongshan.url.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Api(tags = "短域名服务")
@RestController
@RequestMapping("/urlService")
public class UrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "获取短域名", notes = "通过长域名和短域名长度获取短域名")
    @ApiParam(value = "commonUrl是长域名参数，length是需要的段域名长度，范围在[4,8]的整数")
    @GetMapping("/queryShortUrl")
    public String queryShortUrl(String commonUrl, Integer length) {
        if (StringUtils.isBlank(commonUrl) || Objects.isNull(length)) {
            return "长域名和短域名长度都不能为空";
        }
        return shortUrlService.getShortUrl(commonUrl, length);
    }

    @ApiOperation(value = "获取长域名", notes = "通过短域名长度获取长域名")
    @ApiParam(value = "shortUrl是短域名参数")
    @GetMapping("/queryCommonUrl")
    public String queryCommonUrl(String shortUrl) {
        if (StringUtils.isBlank(shortUrl) || shortUrl.length() > 8 || shortUrl.length() < 4) {
            return "短域名不能为空，且长度在[4,8]";
        }
        return shortUrlService.getCommonUrl(shortUrl);
    }
}
