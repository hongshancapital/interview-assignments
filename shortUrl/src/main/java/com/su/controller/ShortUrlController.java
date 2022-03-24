package com.su.controller;

import com.su.service.IShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hejian
 */
@Api(tags = "短链接")
@RestController
@RequestMapping("/url")
@Validated
public class ShortUrlController {

    @Autowired
    private IShortUrlService shortUrlService;

    @ApiOperation("获取短链接")
    @GetMapping("/shortUrl")
    public String getShortUrl(@URL @ApiParam(value = "长连接") @RequestParam("url") String url) {
        return shortUrlService.getShortUrl(url);
    }

    @ApiOperation("获取长链接")
    @GetMapping("/longUrl")
    public String getLongUrl(@URL @ApiParam(value = "短连接") @RequestParam("url") String url) {
        return shortUrlService.getLongUrl(url);
    }
}
