package com.github.shwas1.shorturl.controller;

import com.github.shwas1.shorturl.model.Result;
import com.github.shwas1.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@Api("短链接")
@Validated
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping(path = "/generateShortUrl")
    @ApiOperation("生成短链接")
    public Result generateShortUrl(@ApiParam("原始链接")
                                   @NotEmpty(message = "原始链接不能为空") @URL(message = "链接格式不合法")
                                   @RequestParam("originalUrl") String originalUrl) {
        String shortUrl = shortUrlService.generateShortUrl(originalUrl);
        Assert.hasLength(shortUrl, "短链接不能为空");
        return Result.success(shortUrl);
    }

    @GetMapping(path = "/revertOriginalUrl")
    @ApiOperation("还原原始链接")
    public Result revertOriginalUrl(@ApiParam("短链接")
                                    @NotEmpty(message = "短链接不能为空") @URL(message = "链接格式不合法")
                                    @RequestParam("shortUrl") String shortUrl) {

        String originalUrl = shortUrlService.revertOriginalUrl(shortUrl);
        Assert.hasLength(originalUrl, "原始链接不能为空");
        return Result.success(originalUrl);
    }
}
