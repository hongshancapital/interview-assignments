package com.example.demo.controller;

import com.example.demo.core.web.ApiResponse;
import com.example.demo.core.web.ResponseCode;
import com.example.demo.service.ShortUrlService;
import com.example.demo.utils.ValidUrlUtils;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName ShortURLController
 * @Description 短域名服务接口
 * @Author gongguanghui
 * @Date 2021/11/25 4:16 PM
 * @Version 1.0
 **/
@Api("短域名服务接口文档")
@RestController
@RequestMapping("/shortUrl")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "url", value = "长链接地址", required = true)
            , @ApiImplicitParam(paramType = "query", dataType = "Boolean", name = "check", value = "是否校验链接地址合法性", required = true)})
    @GetMapping("/store")
    public ApiResponse store(@RequestParam(value = "url") String url, @RequestParam(value = "check") Boolean check) {
        ApiResponse response = ApiResponse.buildSuccess();
        if (check && !ValidUrlUtils.validURL(url)) {
            return ApiResponse.buildFail(ResponseCode.ERR_URL_VALID);
        }

        if (Strings.isNullOrEmpty(url)) {
            return ApiResponse.buildFail(ResponseCode.ERR_URL_BLANK);
        }

        String result = shortUrlService.storeShortUrl(url);

        response.setData(result);

        return response;
    }

    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
    @GetMapping("/get")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "url", value = "短链接地址", required = true)})
    public ApiResponse get(@RequestParam(value = "url") String url) {
        ApiResponse response = ApiResponse.buildSuccess();

        if (Strings.isNullOrEmpty(url)) {
            return ApiResponse.buildFail(ResponseCode.ERR_URL_BLANK);
        }

        String result = shortUrlService.getLongUrl(url);
        if (Strings.isNullOrEmpty(result)) {
            return ApiResponse.buildFail(ResponseCode.ERR_URL_NOT_EXIST);
        }

        response.setData(result);
        return response;
    }
}
