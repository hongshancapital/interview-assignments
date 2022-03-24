package com.polly.shorturl.controller;

import com.polly.shorturl.service.IShortUrlService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author polly
 * @date 2022.03.20 10:53:03
 */
@RestController
@Api("短域名服务")
public class ShortUrlController {

    @Autowired
    private IShortUrlService service;

    @ApiOperation(value = "接受长域名信息，返回短域名信息", tags = "短域名存储接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "PathVariable", name = "url", value = "长域名", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "1", response = Integer.class),
            @ApiResponse(code = 200, message = "0", response = Integer.class),
    })
    @PostMapping("/insert")
    public String insertShortUrl(@RequestParam("url") String url) {
        return service.insertShortUrl(url);
    }

    @ApiOperation(value = "接受短域名信息，返回长域名信息", tags = "短域名读取接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "PathVariable", name = "url", value = "短域名", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回一个长域名", response = String.class),
            @ApiResponse(code = 200, message = "返回null", response = String.class)
    })
    @GetMapping("/get")
    public String getUrlByShortUrl(@RequestParam("url") String url) {
        return service.getUrlByShortUrl(url);
    }
}
