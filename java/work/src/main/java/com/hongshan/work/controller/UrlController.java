package com.hongshan.work.controller;

import com.hongshan.work.service.UrlService;
import com.hongshan.work.view.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

@RestController
@Api(value = "短域名服务接口", tags = {"url"})
@RequestMapping(value = "/url", produces = "application/json;charset=UTF-8")
public class UrlController {
    private static final String HTTP_PROTOCOL ="http://";

    @Autowired
    private UrlService service;

    @PostMapping(value="/getShortUrl")
    @ApiOperation("接受长域名信息，返回短域名信息")
    @ResponseBody
    public Result getShortUrl(@ApiParam(value = "需要生成短域名的长域名", required = true)
                                 @RequestParam String url,
                             @ApiParam(value = "URL编码，默认为UTF-8", required = false)
                                @RequestParam(required = false, defaultValue = "UTF-8") String encoding){
        if(!StringUtils.hasLength(url)){
            return Result.create(400,"长链接不能为空", url);
        }
        if(!url.contains("://"))
            url = HTTP_PROTOCOL + url;
        url = UriUtils.encodePath(url, encoding);
        String shortUrl = service.getShortUrl(url);
        if(!StringUtils.hasLength(shortUrl)){
            return Result.error("短域名生成失败", shortUrl);
        }
        return Result.ok("生成短链接成功", shortUrl);
    }

    @RequestMapping(value = "/getLongUrl/{shortUrl}", method = {RequestMethod.GET})
    @ApiOperation("接受短域名信息，返回长域名信息")
    public Result getLongUrl(@ApiParam(value = "短域名链接", required = true)
                                 @PathVariable String shortUrl){
        if(!StringUtils.hasLength(shortUrl) || shortUrl.length() >8){
            return Result.create(400,"短域名链接异常,请检查", shortUrl);
        }
        String longURL = service.getLongUrl(shortUrl);
        if(null != longURL){
            return Result.ok("获取长链接成功", longURL);
        }
        return Result.error("域名信息不存在", longURL);
    }
}