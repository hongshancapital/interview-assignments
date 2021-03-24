package com.wuhui.shorturl.controller;

import com.wuhui.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;


@Api(value = "api", tags = "短链接")
@Validated
@RestController
@RequestMapping("api")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "原始链接转换成短链接")
    @ApiImplicitParam(name = "sourceUrl", value = "原始链接", paramType = "query", required = true)
    @GetMapping("/toShortUrl")
    public String toShortUrl(@NotBlank(message = "sourceUrl 不能为空") String sourceUrl) {
        return shortUrlService.createShortUrl(sourceUrl);
    }

    @ApiOperation(value = "短链接获取原始链接")
    @ApiImplicitParam(name = "shortUrl", value = "短链接", paramType = "query", required = true)
    @GetMapping("/getSourceUrl")
    public String getSourceUrl(@NotBlank(message = "shortUrl 不能为空") String shortUrl) {
        return shortUrlService.findSourceUrl(shortUrl);
    }
}
