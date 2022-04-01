package com.shorts.url.controller;

import com.shorts.url.common.Result;
import com.shorts.url.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 短链接API
 * </p>
 *
 * @author WangYue
 * @date 2022/3/21 18:14
 */
@Api(tags = "短链接API")
@RestController
@RequestMapping("/short/url")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @ApiOperation("根据长链接获取短链接")
    @GetMapping
    public Result<String> getShortUrl(@RequestParam("longUrl") String longUrl) {
        return Result.success(shortUrlService.getShortUrl(longUrl));
    }

    @ApiOperation("根据短链接获取长链接")
    @GetMapping("/long")
    public Result<String> getLongUrl(@RequestParam("shortUrl") String shortUrl) {
        return Result.success(shortUrlService.getLongUrl(shortUrl));
    }
}
