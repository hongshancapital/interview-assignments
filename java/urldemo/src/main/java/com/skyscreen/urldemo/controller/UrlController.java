package com.skyscreen.urldemo.controller;

import com.skyscreen.urldemo.service.ConvertUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/url")
@Api("长短址转换功能控制器")
public class UrlController {

    @Autowired
    private ConvertUrlService convertUrlService;

    @ApiOperation("获取长地址")
    @ApiImplicitParam(name = "shortUrl",value = "短地址参数",dataType = "String")
    @GetMapping(value = "/getLongUrl")
    @ResponseBody
    public String getLongUrl(String shortUrl){
        return convertUrlService.convertToLongUrl(shortUrl);
    }

    @ApiOperation("获取短地址")
    @ApiImplicitParam(name = "longUrl",value = "长地址参数",dataType = "String")
    @GetMapping(value = "/getShortUrl")
    @ResponseBody
    public String getShortUrl(String longUrl){
        return convertUrlService.convertToShortUrl(longUrl);
    }
}
