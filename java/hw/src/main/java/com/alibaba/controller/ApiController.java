package com.alibaba.controller;

import com.alibaba.service.IDomainService;
import com.alibaba.utils.ShortUrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author caozx
 * @desc
 * @date 2022年03月22日 22:01
 */
@Api(tags = "实现短域名服务")
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private IDomainService domainService;

    @ApiOperation(value = "短域名存储接口",notes = "接受长域名信息，返回短域名信息")
    @ApiImplicitParam(name = "url",value = "长域名字符串",dataType = "String")
    @PostMapping(value = "/getShortDomain")
    public String getShortDomain(String url) {
        String shortUrl = domainService.getShortDomain(url);
        return shortUrl;
    }

    @ApiOperation(value = "短域名读取接口",notes = "接受短域名信息，返回长域名信息")
    @ApiImplicitParam(name = "url",value = "短域名字符串",dataType = "String")
    @GetMapping(value = "/getLongDomain/{url}")
    public String getLongDomain(@PathVariable() String url) {
        String longUrl = domainService.getLongDomain(url);
        return longUrl;
    }

}
