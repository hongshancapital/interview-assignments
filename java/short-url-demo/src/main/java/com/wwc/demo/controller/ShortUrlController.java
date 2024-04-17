package com.wwc.demo.controller;

import com.wwc.demo.common.ResponseResult;
import com.wwc.demo.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/s")
@Api(value = "shortUrlController",description = "长短链接转换Controller")
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;
    @ApiOperation(value="获取短连接地址",notes = "获取长链接的短连接地址")
    @RequestMapping(value="/getShortUrl",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public ResponseResult getShortUrl(@RequestParam String longUrl){
        return shortUrlService.getShortUrl(longUrl);
    }
    @ApiOperation(value="获取长链接地址",notes = "根据短码获取长链接地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shortCode", value="短码", required=true, dataType="String", paramType="path")
    })
    @RequestMapping(value="/{shortCode}",method = RequestMethod.PUT,produces="application/json;charset=UTF-8")
    public ResponseResult getLongUrl(@PathVariable("shortCode") String shortCode){
        return shortUrlService.getLongUrl(shortCode);
    }

}
