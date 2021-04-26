package com.wxp.controller;

import com.wxp.common.Message;
import com.wxp.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorturl")
@Api(value = "短链接",tags = {"短链接服务"})
public class ShortUrlController {

    @Autowired
    @Qualifier("shortUrlLocalService")
    private ShortUrlService shortUrlService;

    @GetMapping("get")
    @ApiOperation(value = "获取短链接", notes = "根据原始链接获取短链接")
    public Message<String> shortUrl(@ApiParam("原始链接") @RequestParam String url) {

        return Message.success(shortUrlService.getShortUrl(url));
    }

    @GetMapping("origin")
    @ApiOperation(value = "获取原始链接", notes = "根据短链接获取原始链接")
    public Message<String> originUrl(@ApiParam("短链接") @RequestParam String shortUrl) {

        return Message.success(shortUrlService.getOriginUrl(shortUrl));
    }
}
