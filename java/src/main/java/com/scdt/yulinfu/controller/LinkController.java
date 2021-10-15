package com.scdt.yulinfu.controller;

import com.scdt.yulinfu.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Api(tags = {"短链接转换相关接口"})
@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private DataService dataService;


    @ApiOperation("转换长链接为短链接")
    @GetMapping("short2long")
    public String getShortLink(String longLink) {
        return dataService.getShortLink(longLink);
    }

    @ApiOperation("根据短链接获取长链接")
    @GetMapping("long2short")
    public String getLongLink(String shortLink) {
        return dataService.getLongLink(shortLink);
    }

}
