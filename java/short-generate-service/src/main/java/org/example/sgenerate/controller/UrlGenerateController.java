package org.example.sgenerate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.example.sgenerate.model.ApiResult;
import org.example.sgenerate.model.UrlMappingInfo;
import org.example.sgenerate.service.IUrlGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author liuyadu
 */
@Api(tags = "链接生成器")
@RestController
public class UrlGenerateController {
    @Autowired
    private IUrlGenerateService urlGenerateService;


    /**
     * 生成短链
     *
     * @param url        需要生成的网址
     * @param expiryTime 过期时间
     * @return
     */
    @ApiOperation(value = "生成短链", notes = "生成短链")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "url", value = "需要生成的网址", example = "https://github.com/scdt-china/interview-assignments", required = true, paramType = "form"),
            @ApiImplicitParam(name = "expiryTime", value = "过期时间", example = "2021-09-26 13:00:00", required = false, paramType = "form"),
    })
    @PostMapping(value = "/generate_short_url", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    private ApiResult<UrlMappingInfo> generateShortUrl(@RequestParam(name = "url") String url,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(name = "expiryTime", required = false) Date expiryTime) {
        Assert.hasText(url, "地址不能为空");
        UrlMappingInfo info = urlGenerateService.generateShortUrl(url, expiryTime);
        return ApiResult.ok().data(info);
    }

    /**
     * 读取短链接信息
     *
     * @param url 短链接
     * @return
     */
    @ApiOperation(value = "读取短链接信息", notes = "读取短链接信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "url", value = "短链接", example = "https://s.cn/sK1OxHu", required = true, paramType = "query")
    })
    @GetMapping(value = "/read_short_url")
    private ApiResult<UrlMappingInfo> readShortUrl(@RequestParam(name = "url") String url) {
        Assert.hasText(url, "地址不能为空");
        UrlMappingInfo info = urlGenerateService.readShortUrl(url);
        if (info == null) {
            return ApiResult.failed();
        } else {
            return ApiResult.ok().data(info);
        }
    }
}
