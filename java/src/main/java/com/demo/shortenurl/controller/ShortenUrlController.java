package com.demo.shortenurl.controller;

import com.demo.shortenurl.service.ShortenUrlService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenUrlController {

    @Autowired
    private ShortenUrlService shortenUrlService;

    @ApiOperation(value="短域名读取接口",
            notes="通过短URL获取原始的长URL。注意短域名长度不超过8，由字母和数字组成。如果短域名非法或者不存在，返回一个空值")
    @ApiImplicitParam(name = "shortenUrl", value = "短域名网址" , required = true, dataType = "String")
    @GetMapping(value="/getOriginalUrl")
    public String getOriginalUrl(@RequestBody String shortenUrl) {
        return this.shortenUrlService.getOriginalUrl(shortenUrl);
    }

    @ApiOperation(value="短域名存储接口",
            notes="通过长URL获取短URL。获取的短域名长度不超过8，由字母和数字组成。如果请求数量超出了服务器能力，默认返回ZZZZZZZZ")
    @ApiImplicitParam(name = "originalUrl", value = "原始网址" , required = true, dataType = "String")
    @GetMapping(value="/getShortenUrl")
    public String getShortenUrl(@RequestBody String originalUrl) {
        return this.shortenUrlService.getShortenUrl(originalUrl);
    }
}
