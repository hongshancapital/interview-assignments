package com.example.demo.controller;

import com.example.demo.service.TransformUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author 845627580@qq.com
 * @Version 1.0
 **/
@Api(value="域名转换")
@RestController
@RequestMapping("/UrlTransform")
public class UserController {

    @Autowired
    private TransformUrlService transformUrlService;

    @ApiOperation(value="短域名读取接口 接受短域名信息，返回长域名信息")
    @PostMapping("ShortUrlTransformLongUrl")
    public String ShortUrlTransformLongUrl(String url){
        return  transformUrlService.ShortUrlTransformLongUrl(url);
    }


    @ApiOperation(value="短域名存储接口 接受长域名信息，返回短域名信息")
    @PostMapping("longUrlTransformShortUrl")
    public String LongUrlTransformShortUrl(String url){
        return  transformUrlService.LongUrlTransformShortUrl(url);
    }

}
