package com.zs.shorturl.controller;

import com.zs.shorturl.enity.vo.Result;
import com.zs.shorturl.service.IShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author zs
 * @date 2021/5/9
 */
@RestController
@RequestMapping("/api")
@Api(value = "短链接相关接口")
public class ShrotUrlController {

    @Autowired
    private IShortUrlService shortUrlService;

    @ApiOperation(value = "通过长链接获取短链接")
    @ApiImplicitParam(name = "url", value = "长链接", required = true, dataType = "String", paramType = "query")
    @GetMapping("/shorturl")
    public Result getShortUrl(@PathParam("url") String url){
      return shortUrlService.getShortUrlFromLongUrl(url);
    }


    @ApiOperation(value = "通过短链接获取长链接")
    @ApiImplicitParam(name = "url", value = "短链接", required = true, dataType = "String", paramType = "query")
    @GetMapping("/longurl")
    public Result getLongUrl(@PathParam("url") String url){
        return shortUrlService.getLongUrlFromShortUrl(url);
    }



}
