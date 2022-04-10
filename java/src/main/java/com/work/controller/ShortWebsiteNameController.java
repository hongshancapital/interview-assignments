package com.work.controller;

import com.work.model.Result;
import com.work.service.ShortWebsiteNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "短域名服务")
@RestController
@RequestMapping("/shortWebsite")
public class ShortWebsiteNameController {

    @Autowired
    private ShortWebsiteNameService shortWebsiteNameService;

    @ApiOperation(value = "长域名转换短域名存储并返回短域名", httpMethod = "GET", response = Result.class)
    @GetMapping("/convertToShortName")
    public Result<String> convertToShortName(
            @ApiParam(value = "长网站url") String longName) {
        return shortWebsiteNameService.convertToShortName(longName);
    }

    @ApiOperation(value = "根据短域名信息返回长域名信息", httpMethod = "GET", response = Result.class)
    @GetMapping("/getLongName")
    public Result<String> getLongName(@ApiParam(value = "短网站url") String shortName) {
        return shortWebsiteNameService.getLongName(shortName);
    }
}
