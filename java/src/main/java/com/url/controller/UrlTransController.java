package com.url.controller;

import com.url.bean.UrlResultBean;
import com.url.service.UrlTransService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 域名转换控制类
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
@RestController
@RequestMapping("url")
@Api(tags = "长域名转短域名Controller")
public class UrlTransController {


    @Autowired
    private UrlTransService urlTransService;

    @ApiOperation(value = "生成短域名",notes = "根据长域名生成短域名")
    @ApiImplicitParam(name = "longUrl",value = "长域名",dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200,message = "请求成功"),
            @ApiResponse(code = 400,message = "域名参数格式错误"),
            @ApiResponse(code = 500,message = "服务未知异常"),
    })
    @PostMapping(value = "/getShort")
    public UrlResultBean getShortUrl(String longUrl){
        UrlResultBean urlResultBean = urlTransService.getShortUrl(longUrl);
        return urlResultBean;
    }

    @ApiOperation(value = "获取长域名",notes = "根据短域名获取长域名")
    @ApiImplicitParam(name = "shortUrl",value = "短域名",dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200,message = "请求成功"),
            @ApiResponse(code = 400,message = "域名参数格式错误"),
            @ApiResponse(code = 500,message = "服务未知异常"),
            @ApiResponse(code = 600,message = "长域名不存在或已过期"),
    })
    @GetMapping(value = "/getLong")
    public UrlResultBean getLongUrl(String shortUrl){
        UrlResultBean urlResultBean = urlTransService.getLongUrl(shortUrl);
        return urlResultBean;
    }

}
