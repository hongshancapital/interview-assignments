package com.example.demo.controller;

import com.example.demo.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/urlConvert")
@Api(tags = "UrlConvert")
public class UrlConvertController {

    @Autowired
    private UrlService urlService;

    @ApiOperation(value = "生成短域名", notes = "生成短域名")
    @GetMapping("/getShortUrl")
    public String getShortUrl(@ApiParam(value = "长域名") @RequestParam String url) {
        return urlService.getShortUrl(url);
    }

    @ApiOperation(value = "短域名转换长域名", notes = "短域名转换长域名")
    @GetMapping("/convertToLongUrl")
    public void convertToLongUrl(@ApiParam(value = "短域名") @RequestParam String url, HttpServletResponse
                                response ) throws IOException {
        String longUrl =  urlService.convertToLongUrl(url);
        System.out.println(longUrl);
        response.sendRedirect(longUrl);

    }

    @GetMapping("/redirectUrl")
    public String redirectUrl() {
        return "302 page";
    }
}
