package com.zc.shorturl.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.zc.shorturl.dto.CreateShortUrlRequest;
import com.zc.shorturl.service.ShortUrlService;
import com.zc.shorturl.config.ShortUrlConfig;
import com.zc.shorturl.dto.BaseResponse;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1")
public class ShortUrlController {
    private final ShortUrlService shortUrlService;
    private final ShortUrlConfig shortUrlConfig;

    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping(path = "/create")
    @ResponseBody
    public ResponseEntity<BaseResponse> CreateShortUrl(@RequestBody @Validated CreateShortUrlRequest request){
        // TODO:生产屏蔽日志
        log.info(request.getLongUrl());
        String shortUrl = shortUrlService.createShortUrl(request.getLongUrl());
        // 更新缓存，且确保long2short与short2long更新时间一致，提供保证一致性的条件
        shortUrlService.updateShort2Long(shortUrl, request.getLongUrl());
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.ok(shortUrlConfig.getDefaultCustomDomain() + "/" + shortUrl));
    }
}
