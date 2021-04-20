package com.example.demo.controller;

import com.example.demo.model.CommonResponse;
import com.example.demo.service.UrlGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 短链接生成接口
 */
@Slf4j
@Api("短链接服务接口")
@RestController("/shortUrl")
public class UrlGeneratorController {


    @Autowired
    private UrlGeneratorService urlGeneratorService;

    @GetMapping("/generate")
    @ApiOperation("生成短链接")
    public CommonResponse<String> generateShortUrl(@ApiParam(name = "url", value = "原始链接", required = true) @RequestParam(name = "url", required = true) String url) {
        String shortUrl = urlGeneratorService.generateShortUrl(url);
        return CommonResponse.success(shortUrl);
    }

    @GetMapping("/originUrl")
    @ApiOperation("获取原始链接")
    public CommonResponse<String> findOriginUrl(@ApiParam(name = "shortUrl", value = "短链接", required = true) @RequestParam(name = "shortUrl", required = true) String shortUrl) {
        String originUrl = urlGeneratorService.findOriginUrl(shortUrl);
        return CommonResponse.success(originUrl);
    }
}
