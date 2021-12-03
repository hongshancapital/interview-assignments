package com.example.demo.controller;

import com.example.demo.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;


@Api(tags = "短链接服务")
@RestController
@RequestMapping(path = "/short-url/v1", produces = "application/json")
@Validated
public class ShortUrl {


    @Autowired
    private ShortUrlService shortUrlService;


    /**
     * 根据完整url，生成短连接
     *
     * @param longUrl
     * @return
     */
    @ApiOperation(value = "根据长url，生成短url", httpMethod = "GET")
    @GetMapping(path = "/shortUrl")
    public String getShortUrl(@RequestParam String longUrl) {
        return shortUrlService.createShortUrl(longUrl);
    }

    /**
     * 根据完整shortUrl，返回Longurl
     * 没找到对应longUrl 返回null
     *
     * @param shortUrl
     * @return
     */
    @ApiOperation(value = "根据短url，返回长url", httpMethod = "GET")
    @GetMapping(path = "/longUrl")
    public String getLongUrl(@RequestParam String shortUrl) {
        return shortUrlService.getLongUrl(shortUrl);
    }


}
