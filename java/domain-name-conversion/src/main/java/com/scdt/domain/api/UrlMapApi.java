package com.scdt.domain.api;

import com.scdt.domain.common.Result;
import com.scdt.domain.service.UrlMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Api("长短链接转换api")
@RestController
public class UrlMapApi {

    @Resource
    private UrlMapService urlMapService;

    @PostMapping(value = "/longToShort")
    @ApiOperation("根据长链接转短链接接口")
    @ApiImplicitParam(name = "url", value = "长链接地址", dataType = "String")
    public Result longToShort(String url) {
        return Result.success(urlMapService.longToShort(url));
    }

    @GetMapping(value = "/shortToLong/{code}")
    @ApiOperation("根据短链接获取长链接接口")
    @ApiImplicitParam(name = "code", value = "短链接地址", dataType = "String")
    public Result shortToLong(@PathVariable("code") String code) {
        return Result.success(urlMapService.shortToLong(code));
    }
}
