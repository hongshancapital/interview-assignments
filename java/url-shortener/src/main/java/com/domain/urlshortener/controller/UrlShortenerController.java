package com.domain.urlshortener.controller;

import com.domain.urlshortener.vo.ShortUrlCreateRequest;
import com.domain.urlshortener.vo.ShortUrlCreateResponse;
import com.domain.urlshortener.vo.ShortUrlGetRequest;
import com.domain.urlshortener.vo.ShortUrlGetResponse;
import com.domain.urlshortener.model.ResponseModel;
import com.domain.urlshortener.service.UrlShortenerService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 0:19
 */
@Api(tags = "短链接口")
@RestController
@RequestMapping(value = "/url/shortener")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Operation(summary = "短域名存储接口", description = "接受长域名信息，返回短域名信息")
    @PostMapping(value = "/create")
    public ResponseModel<ShortUrlCreateResponse> createShortUrl(@RequestBody @Validated ShortUrlCreateRequest request) {
        ShortUrlCreateResponse response = new ShortUrlCreateResponse(request.getUrl(), urlShortenerService.createShortUrl(request.getUrl()));
        return ResponseModel.ok(response);
    }

    @Operation(summary = "短域名读取接口", description = "接受短域名信息，返回长域名信息")
    @GetMapping(value = "/retrieve")
    public ResponseModel<ShortUrlGetResponse> retrieveLongUrl(@RequestBody @Validated ShortUrlGetRequest request) {
        ShortUrlGetResponse response = new ShortUrlGetResponse(urlShortenerService.getLongUrl(request.getUrl()), request.getUrl());
        return ResponseModel.ok(response);
    }

}
