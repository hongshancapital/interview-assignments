package com.scdt.shorturl.controller;
import com.scdt.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短URL服务接口
 *
 * @author niuyi
 * @@since 2021-12-11
 */
@Api(tags = "短URL服务接口")
@RestController
@RequestMapping("/api")
public class ShortUrlController {

    //短URL服务
    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "短URL服务接口", notes = "短URL服务接口")
    @PostMapping("/toShortURL")
    public String toShortURL(@RequestParam String longUrl) { return shortUrlService.toShortURL(longUrl); }

    @ApiOperation(value = "短URL还原成原始URL接口", notes = "短URL还原成原始URL接口")
    @PostMapping("/toLongUrl")
    public String toLongUrl(String shortURL) {
        return shortUrlService.toLongUrl(shortURL);
    }
}
