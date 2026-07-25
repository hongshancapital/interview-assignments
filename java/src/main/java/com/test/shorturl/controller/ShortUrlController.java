package com.test.shorturl.controller;

import com.test.shorturl.common.exception.ShortUrlException;
import com.test.shorturl.common.result.Result;
import com.test.shorturl.common.result.ResultCodeEnum;
import com.test.shorturl.config.Configer;
import com.test.shorturl.service.ShortUrlService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: liurenyuan
 * @Date: 2021/11/10
 * @Version: 1.0
 */
@Api(tags = "短地址管理")
@RestController
public class ShortUrlController {
    @Resource
    Configer configer;
    @Resource
    ShortUrlService shortUrlService;

    @ApiOperation("根据长地址返回短地址")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "longUrl", value = "长地址", required = true) })
    @GetMapping("/getLongToShort")
    public Result getLongToShort(@RequestParam String longUrl) {
        if (longUrl == null || longUrl.trim().length()==0) {
            throw new ShortUrlException(ResultCodeEnum.ORIGINAL_NOT_EMPTY, configer.writableStackTrace);
        }
        String shourUrl = shortUrlService.longToShort(longUrl);
        return Result.success(shourUrl);

    }
    @ApiOperation("根据短地址返回长地址")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "shortUrl", value = "短地址", required = true) })
    @GetMapping("/getShortToLong")
    public Result getShortToLong(@RequestParam String shortUrl) {
        if (shortUrl == null || shortUrl.trim().length()==0) {
            throw new ShortUrlException(ResultCodeEnum.SHORT_NOT_EMPTY,configer.writableStackTrace);
        }
        String originalUrl = shortUrlService.shortToLong(shortUrl);
        return Result.success(originalUrl);
    }
    @ApiOperation("获取总的映射数量")
    @GetMapping("/count")
    public Result count() {
        return Result.success(shortUrlService.count());
    }
}
