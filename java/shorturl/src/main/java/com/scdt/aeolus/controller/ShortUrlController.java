package com.scdt.aeolus.controller;

import com.scdt.aeolus.controller.dto.*;
import com.scdt.aeolus.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
@Slf4j
@Api("短域名服务")
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping("/getShortUrl")
    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    public BaseResponse getShortUrl(@RequestBody GetShortUrlRequest request) {
        String shortUrl = shortUrlService.saveUrl(request.getOriginalUrl());
        return new BaseResponse(new GetShortUrlResponse(shortUrl));
    }

    @PostMapping("/getOriginalUrl")
    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    public BaseResponse getOriginalUrl(@RequestBody GetOriginalUrlRequest request) {
        Optional<String> originalUrl = shortUrlService.getUrl(request.getShortUrl());
        if (originalUrl.isPresent()) {
            return new BaseResponse(new GetOriginalUrlResponse(originalUrl.get()));
        } else {
            return new BaseResponse(ErrorCode.OriginalUrlNotExist, "cannot find original url");
        }
    }
}