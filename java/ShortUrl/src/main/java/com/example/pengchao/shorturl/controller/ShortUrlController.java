package com.example.pengchao.shorturl.controller;

import com.example.pengchao.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author pengchao04
 * @date 2022/5/23 10:55
 */
@Controller
@Api(tags = "短域名服务")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation("接受短域名信息，返回长域名信息")
    @GetMapping("/url/getLong")
    public String getLongUrl(@RequestParam("shortUrl") @NotBlank String shortUrl){
        return shortUrlService.getLongUrl(shortUrl);
    }

    @ApiOperation("接受长域名信息，返回短域名信息")
    @GetMapping("/url/getShort")
    public String getShortUrl(@RequestParam("longUrl") @NotBlank String longUrl){
        return shortUrlService.getShortUrl(longUrl);
    }
}
