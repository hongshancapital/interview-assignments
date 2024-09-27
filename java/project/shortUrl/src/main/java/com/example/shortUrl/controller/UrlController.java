package com.example.shortUrl.controller;

import com.example.shortUrl.pojo.Result;
import com.example.shortUrl.service.UrlHandlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author HOPE
 * @Description url请求层
 * @Date 2022/4/28 19:51
 */
@Api(tags = "长短域名转换")
@RestController
@RequestMapping("/urlMap")
public class UrlController {
    @Autowired
    UrlHandlerService urlHandlerService;

    @GetMapping("/getLongUrl")
    @ApiOperation("查询长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String")
    public Result<String> queryLongUrl(String shortUrl) {
        return urlHandlerService.getLongUrl(shortUrl);
    }

    @GetMapping("/getShortUrl")
    @ApiOperation("查询短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String")
    public Result<String> queryShortUrl(String longUrl) {
        return urlHandlerService.getShortUrl(longUrl);
    }


}

