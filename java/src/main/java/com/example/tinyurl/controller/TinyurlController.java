package com.example.tinyurl.controller;

import com.example.tinyurl.service.TinyurlService;
import com.example.tinyurl.util.SpringBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短网址转换接口类
 */
@RestController
@RequestMapping("/tinyurl")
@Api(tags = "TinyurlController")
public class TinyurlController {
    @RequestMapping(path = "/encode", method = RequestMethod.POST)
    @ApiOperation(value="/encode", notes = "短域名存储接口", response=String.class, httpMethod="POST")
    public String encode(String longUrl) {
        TinyurlService tinyurlService = SpringBeanUtil.getBean(TinyurlService.class);
        return tinyurlService.encode(longUrl);
    }

    @RequestMapping(path = "/decode", method = RequestMethod.GET)
    @ApiOperation(value="/decode", notes = "短域名读取接口", response=String.class, httpMethod="GET")
    public String decode(String tinyUrl) {
        TinyurlService tinyurlService = SpringBeanUtil.getBean(TinyurlService.class);
        return tinyurlService.decode(tinyUrl);
    }
}
