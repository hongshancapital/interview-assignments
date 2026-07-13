package com.liupf.tiny.url.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.liupf.tiny.url.common.ApiResult;
import com.liupf.tiny.url.enums.ApiCodeEnum;
import com.liupf.tiny.url.service.ITinyURLService;
import com.liupf.tiny.url.utils.URLUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/url")
@Api(value = "URL转换接口", tags = {"URL转换接口"})
public class TinyURLController {

    @Resource
    private ITinyURLService tinyURLService;

    /**
     * 将长网址编码为短网址
     */
    @PostMapping("/getShort")
    @ApiOperation("长链接 -> 短链接")
    @ResponseBody
    @ApiParam(name = "longUrl", value = "长链接")
    public ApiResult<String> saveLongUrl(String longUrl) {
        ApiCodeEnum validRes = validUrl(longUrl);
        if (validRes != null) {
            ApiResult<String> result = ApiResult.failed(validRes);
            log.warn("save long url fail. result:{}, long-url:{}", result.toString(), longUrl);
            return result;
        }
        String shortUrl = tinyURLService.saveLongUrl(longUrl);
        return ApiResult.ok(shortUrl);
    }

    /**
     * 将短网址解码为长网址
     */
    @PostMapping("/getLong")
    @ApiOperation("短链接 -> 长链接")
    @ResponseBody
    @ApiParam(name = "shortUrl", value = "短链接")
    public ApiResult<String> getLongURL(String shortUrl) {
        ApiCodeEnum validRes = validUrl(shortUrl);
        if (validRes != null) {
            return ApiResult.failed(validRes);
        }
        String longUrl = tinyURLService.getLongUrl(shortUrl);
        if (StringUtils.isBlank(longUrl)) {
            return ApiResult.failed(ApiCodeEnum.URL_NOT_EXISTS);
        }
        return ApiResult.ok(longUrl);
    }

    /**
     * 验证URL规范
     */
    private ApiCodeEnum validUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return ApiCodeEnum.PARAM_ERROR;
        }
        // 符合大部分浏览器限制
        if (url.length() > 2048) {
            return ApiCodeEnum.URL_TOO_LONG;
        }
        if (!URLUtil.isUrl(url)) {
            return ApiCodeEnum.URL_ERROR;
        }
        return null;
    }

}
