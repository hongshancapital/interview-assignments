package com.scdt.demo.api.controller;

import com.scdt.demo.common.URLUtils;
import com.scdt.demo.common.web.CodeMessage;
import com.scdt.demo.common.web.Response;
import com.scdt.demo.service.ShortURLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("短网址相关接口")
@RestController
@RequestMapping("/api/url")
public class ShortURLController {

    private final ShortURLService shortUrlService;

    @Autowired
    public ShortURLController(ShortURLService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @ApiOperation("根据长网址获取短网址")
    @GetMapping("/shorten")
    public Response<String> shortenUrl(@ApiParam(name = "长网址", required = true, example = "https://www.google.com")
                                           @RequestParam String originUrl) {
        if (!URLUtils.isValidUrl(originUrl)) {
            return Response.fail(CodeMessage.INVALID_URL);
        }
        String shortUrl = shortUrlService.shortenUrl(originUrl);
        return Response.ok(shortUrl);
    }

    @ApiOperation("根据短网址获取长网址")
    @GetMapping("/origin")
    public Response<String> getOriginUrl(@ApiParam(name = "短网址", required = true, example = "sf2Uj0")
                                             @RequestParam String shortUrl) {
        if (!URLUtils.isValidShortUrl(shortUrl)) {
            return Response.fail(CodeMessage.INVALID_SHORT_URL);
        }
        String originUrl = shortUrlService.getOriginUrl(shortUrl).orElse("");
        return Response.ok(originUrl);
    }
 }
