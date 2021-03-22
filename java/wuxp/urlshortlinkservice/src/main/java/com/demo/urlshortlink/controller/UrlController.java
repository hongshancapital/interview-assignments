package com.demo.urlshortlink.controller;

import com.demo.urlshortlink.dto.UrlLongRequest;
import com.demo.urlshortlink.service.UrlService;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UrlController {


    private final UrlService urlService;
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ApiOperation(value = "Convert long url", notes = "转换长URL到短URL")
    @PostMapping("generate-short")
    public String convertToShortUrl(@RequestBody UrlLongRequest request) {
        return urlService.createAndSaveLink(request);
    }

    @ApiOperation(value = "getLongurl", notes = "获取长URl")
    @GetMapping(value = "{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public String getAndRedirect(@PathVariable String shortUrl) throws Exception {
        String url = urlService.getLongUrl(shortUrl);
        return url;
    }
}
