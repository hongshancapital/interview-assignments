package com.gaohf.shortener.controller;

import com.gaohf.shortener.commons.response.ResponseResult;
import com.gaohf.shortener.service.IUrlShortenerService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value="短域名",tags={"短域名服务"})
@RestController
@RequestMapping(path = "/v1")
public class UrlShortenerController {

    @Autowired
    IUrlShortenerService urlShortenerService;

    @Operation(summary = "获取短链接id")
    @PostMapping(path = "/create")
    public ResponseResult create(@RequestParam(value = "url", required = true)String url) {
        return urlShortenerService.create(url);
    }

    @Operation(summary = "根据id获取长链接")
    @GetMapping(path = "/getUrl")
    public ResponseResult getUrl(@RequestParam(value = "id", required = true) String id) {
        return urlShortenerService.getUrl(id);
    }
}
