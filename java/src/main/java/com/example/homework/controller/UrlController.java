package com.example.homework.controller;

import com.example.homework.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Karl
 * @date: 2022/4/27
 */
@Api(tags = "url管理")
@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @ApiOperation(value = "短域名读取接口")
    @GetMapping(value = "/getLong")
    public String getLongUrl(@RequestParam String shortUrl){
        return urlService.getLongUrl(shortUrl);
    }

    @ApiOperation(value = "短域名存储接口")
    @GetMapping(value = "/saveLong")
    public String saveLongUrl(@RequestParam String longUrl){
        return urlService.saveLongUrl(longUrl);
    }
}