package com.scdt.controller;

import com.scdt.model.Result;
import com.scdt.model.request.UrlRequest;
import com.scdt.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController("/url")
public class UrlController {
    @Autowired
    UrlService urlService;

    @GetMapping("/l2s")
    public Result<String> long2Short(
            @RequestParam String url, HttpServletResponse response
    ) {

        return Result.success(urlService.long2Short(url));
    }

    @GetMapping("/s2l")
    public Result<String> short2Long(
            @RequestParam String url, HttpServletResponse response
    ) {

        return Result.success(urlService.short2Long(url));
    }
}
