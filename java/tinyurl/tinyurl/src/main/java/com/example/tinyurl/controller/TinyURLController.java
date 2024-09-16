package com.example.tinyurl.controller;

import com.example.tinyurl.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangtian
 * @version 1.0
 * @Project: TinyURLController
 * @Title: tinyurl
 * @Package com.example.tinyurl.controller
 * @Description:长短链接互转服务入口
 * @date 2021/12/19 14:30
 */

@RestController
@Api(value = "短域名接口", tags="短域名接口", description = "短域名接口")
public class TinyURLController {

    @Autowired
    UrlService urlServiceImpl;

    @GetMapping("/toTinyurl")
    @ApiOperation("长链接转短链接")
    public String toTinyurl(@ApiParam(value = "长链接url", required = true) @RequestParam(value = "url")String url) {
        return urlServiceImpl.toTinyurl(url);
    }

    @GetMapping("/fromTinyurl/{url}")
    @ApiOperation("短链接转长链接")
    public String fromTinyurl(@ApiParam(value = "短链接url", required = true) @PathVariable("url") String url) {
        return urlServiceImpl.fromTinyurl(url);
    }
}
