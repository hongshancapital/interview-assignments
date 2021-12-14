package com.scdt.controller;

import com.scdt.model.Result;
import com.scdt.model.request.UrlRequest;
import com.scdt.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/url")
public class UrlController {
    @Autowired
    UrlService urlService;

    @PostMapping("/l2s")
    public Result<String> long2Short(
            @RequestBody UrlRequest request
    ) {

        return Result.success(urlService.long2Short(request));
    }
    @PostMapping("/s2l")
    public Result<String> short2Long(
            @RequestBody UrlRequest request
    ) {

        return Result.success(urlService.short2Long(request));
    }
}
