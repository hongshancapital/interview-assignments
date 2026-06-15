package com.zmc.shorturl.controller;


import com.zmc.shorturl.exceptions.InvalidParameterException;
import com.zmc.shorturl.service.ShortUrlService;
import com.zmc.shorturl.utils.HttpUtils;
import com.zmc.shorturl.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * Description: 短链接服务控制层
 * Author: Zmc
 * Date: 2021-12-11 18:20
 **/
@RestController
@RequestMapping("api")
@ApiOperation("长域名、短域名相互转换接口")
@RequiredArgsConstructor
public class ShortUrlController {


    private final ShortUrlService shortUrlService;



    @PostMapping("/shortUrl")
    @ApiOperation("长域名获取短域名")
    public Result<String> shortUrl(@ApiParam("需要转换的长域名") @RequestParam String longUrl) throws ExecutionException {
        if (!HttpUtils.verifyUrl(longUrl)) {
            throw new InvalidParameterException("longUrl:" + longUrl + "错误");
        }
        String shortUrl = shortUrlService.shortUrl(longUrl);
        return Result.success(shortUrl);
    }


    @GetMapping("longUrl")
    @ApiOperation(value = "通过短域名获取长域名", protocols = "application/json")
    public Result<String> longUrl(@ApiParam("短域名") @RequestParam String shortUrl) {
        String longUrl = shortUrlService.parseLongUrl(shortUrl);
        return Result.success(longUrl);
    }
}
