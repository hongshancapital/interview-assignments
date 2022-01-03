package com.homework.shorturl.controller;

import com.homework.shorturl.RestResponse;
import com.homework.shorturl.exception.ShortUrlBusinessException;
import com.homework.shorturl.req.FullUrlReq;
import com.homework.shorturl.req.ShortUrlReq;
import com.homework.shorturl.res.FullUrlRes;
import com.homework.shorturl.res.ShortUrlRes;
import com.homework.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "Short Url Controller")
@RequestMapping
@AllArgsConstructor
@Slf4j
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    @PostMapping("/shortUrl")
    @ApiOperation("根据长链接生成短链接")
    public RestResponse<ShortUrlRes> generateShortUrlByFullUrl(@RequestBody @Valid ShortUrlReq shortUrlReq) {
        return new RestResponse<>(shortUrlService.generateShortUrlByFullUrl(shortUrlReq.getUrl()));
    }

    @PostMapping("/fullUrl")
    @ApiOperation("根据短链接获取长链接")
    public RestResponse<FullUrlRes> getFullUrlByShortUrl(@RequestBody @Valid FullUrlReq fullUrlReq) {
        return new RestResponse<>(shortUrlService.getFullUrlByShortUrl(fullUrlReq.getUrl()));
    }

    @ExceptionHandler
    public RestResponse handleExeption(ShortUrlBusinessException ex) {
        log.info("Exception handle: {}", ex.getMessage());
        return new RestResponse(ex.getCode(), ex.getMessage());
    }
}
