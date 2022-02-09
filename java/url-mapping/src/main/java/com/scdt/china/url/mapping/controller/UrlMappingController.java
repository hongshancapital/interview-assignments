package com.scdt.china.url.mapping.controller;

import com.scdt.china.url.mapping.service.IUrlMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urlMapping")
@Api(value = "长域名、短域名相互转换接口", protocols = "application/json", produces = "application/json")
public class UrlMappingController {

    @Autowired
    private IUrlMappingService urlMappingService;

    /**
     * 获取转换后的域名
     * @param originalUrl
     * @return
     */
    @RequestMapping(value="/getShortUrl")
    @ApiOperation("长域名转短")
    public String getShortUrl(String originalUrl) {
        if(originalUrl==null || originalUrl.trim()==""){
            //如原始url为null直接返回
            return null;
        }

        return urlMappingService.getShortUrl(originalUrl);
    }

    /**
     * 获取转换前的原始域名
     * @param shortUrl
     * @return
     */
    @RequestMapping(value="/getOrinigalUrl")
    @ApiOperation("获取原始域名")
    public String getOrinigalUrl(String shortUrl) {
        if(shortUrl==null || shortUrl.trim()==""){
            //如传入的url为null直接返回
            return shortUrl;
        }

        return urlMappingService.getOrinigalUrl(shortUrl);
    }
}
