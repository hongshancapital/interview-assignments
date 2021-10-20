package com.assignment.controller;

import com.assignment.service.DomainService;
import com.assignment.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短域名服务控制器
 *
 * @author shifeng
 */
@RestController
@RequestMapping("/api/domain")
@Api(tags = {"域名转换服务"}, value = "长短域名转换服务")
public class DomainController {
    @Autowired
    private DomainService domainService;

    @GetMapping("/getShortUrl")
    @ApiOperation("获取短域名")
    public Result getShortUrl(@RequestParam String longUrl) {
        return domainService.getShortUrl(longUrl);
    }


    @GetMapping("/getLongUrl")
    @ApiOperation("查询长域名")
    public Result getLongUrl(@RequestParam String shortUrl) {
        return domainService.getLongUrl(shortUrl);
    }
}
