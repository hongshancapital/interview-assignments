package com.scdt.interview.leon.controller;

import com.scdt.interview.leon.bean.RecoverUrlRO;
import com.scdt.interview.leon.bean.ShortenUrlRO;
import com.scdt.interview.leon.service.UrlService;
import com.scdt.interview.leon.spec.MResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 短域名控制器
 *
 * @author leon
 * @since 2021/10/26
 */
@Slf4j
@RestController
@RequestMapping("/url")
@Api(tags = "短域名服务")
public class UrlController {

    @Autowired
    private UrlService urlService;


    @ApiOperation(value = "缩短链接")
    @PostMapping("/shorten")
    public MResponse<String> shorten(@RequestBody @Valid ShortenUrlRO urlRO) {
        String shortUrl =  urlService.shorten(urlRO.getOriUrl());
        return MResponse.success(shortUrl);
    }

    @ApiOperation(value = "恢复链接")
    @PostMapping("/recover")
    public MResponse<String> recharge( @RequestBody @Valid RecoverUrlRO urlRO) {
        String shortUrl =  urlService.recover(urlRO.getShortUrl());
        return MResponse.success(shortUrl);
    }

//    @ApiOperation(value = "缓存容量")
//    @PostMapping("/cache-size")
//    public MResponse<Integer> cacheSize( ) {
//        return MResponse.success(urlService.cacheSize());
//    }
}
