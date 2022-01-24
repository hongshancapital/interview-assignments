package com.controller;

import com.common.Result;
import com.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    public UrlService urlService;

    @PostMapping("addUrl")
    public Result<String> addUrl(String url){
        return new Result<>(urlService.addUrl(url));
    }

    @GetMapping("getUrl")
    public Result<String> getUrl(String shortUrl){
        return new Result<>(urlService.getUrl(shortUrl));
    }
}
