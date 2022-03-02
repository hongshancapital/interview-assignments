package org.faof.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.faof.domain.BaseRequest;
import org.faof.domain.BaseResponse;
import org.faof.domain.ResponseFactory;
import org.faof.service.ILongShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("系统接口")
public class ApplicationController {

    @Autowired
    ILongShortUrlService longShortUrlService;

    @RequestMapping(value = "health", method = {RequestMethod.GET})
    @ApiOperation("检查服务运行状态")
    public String health() {
        return "ok!";
    }

    @RequestMapping(value = "long2short", method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation("长链接生成短链接")
    public BaseResponse<?> long2short(@RequestBody BaseRequest requet) {
        String shortUrl = longShortUrlService.getLong2ShortUrl(requet.getLongUrl());
        return ResponseFactory.success(requet.getLongUrl(), shortUrl);
    }

    @RequestMapping(value = "short2long", method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation("根据短链接查询长链接")
    public BaseResponse<?> short2long(@RequestBody BaseRequest requet) {
        String longUrl = longShortUrlService.getShort2LongUrl(requet.getShortUrl());
        return ResponseFactory.success(longUrl, requet.getShortUrl());
    }

}
