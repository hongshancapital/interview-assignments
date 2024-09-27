package com.url.controller;

import com.url.serveice.UrlExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "长短连接转换服务")
@RestController
@RequestMapping("change")
public class UrlExchangeController {

    @Autowired
    private UrlExchangeService service;

    @ApiOperation(value = "长连接转短链接")
    @GetMapping("/get/short")
    public String getShortUrl(@ApiParam(value = "长连接") String longUrl){
        System.out.println(longUrl);
        return service.getShort(longUrl);
    }

    @ApiOperation(value = "根据短链接获取长连接")
    @GetMapping("/get/longUrl")
    public String getLongUrl(@ApiParam(value = "短连接")String shortUrl){
        System.out.println(shortUrl);
        return service.getLong(shortUrl);
    }

}
