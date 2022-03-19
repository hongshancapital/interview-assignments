package com.github.shwas1.shorturl.controller;

import com.github.shwas1.shorturl.model.Result;
import com.github.shwas1.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("短链接")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping(path = "/generateShortUrl")
    @ApiOperation("生成短链接")
    public Result generateShortUrl(@ApiParam("原始链接") @RequestParam("originalUrl") String originalUrl) {
        Assert.hasLength(originalUrl, "原始链接不能为空");

        String shortUrl = shortUrlService.generateShortUrl(originalUrl);
        Assert.hasLength(shortUrl, "短链接不能为空");
        return Result.success(shortUrl);
    }

    @GetMapping(path = "/revertOriginalUrl")
    @ApiOperation("还原原始链接")
    public Result revertOriginalUrl(@ApiParam("短链接") @RequestParam("shortUrl") String shortUrl) {
        Assert.hasLength(shortUrl, "短链接不能为空");

        String originalUrl = shortUrlService.revertOriginalUrl(shortUrl);
        Assert.hasLength(originalUrl, "原始链接不能为空");
        return Result.success(originalUrl);
    }
}
