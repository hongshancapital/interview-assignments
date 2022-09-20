package com.lisi.urlconverter.controller;

import com.lisi.urlconverter.service.ConverterService;
import com.lisi.urlconverter.util.ResponseUtil;
import com.lisi.urlconverter.vo.UCResponse;
import constant.CommonConstant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 提供域名转换服务
 * @author: li si
 */

@Api(tags = "域名转换服务controller")
@RestController
public class ConverterController {

    @Autowired
    private ConverterService converterService;

    @PostMapping("/convert")
    @ApiOperation(value = "将长域名转换成短域名", httpMethod = "POST", notes = "长域名字符长度不能超过1000")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "需要转换的长域名", required = true, paramType = "query", dataType = "String")
    })
    public UCResponse convert(String url) {
        if (StringUtils.isEmpty(url) || url.length() > CommonConstant.LONG_URL_LENGTH_LIMIT) {
            return ResponseUtil.builderErrorResponse(CommonConstant.VALIDATION_FAILED_RESPONSE_CODE, "参数长度超出限制");
        }
        String shortUrl = converterService.convert(url);
        return ResponseUtil.buildSuccessResponse(shortUrl);
    }

    @PostMapping("/get")
    @ApiOperation(value = "根据短域名，获取长域名", httpMethod = "POST", notes = "短域名字符长度不能超过8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "短域名", required = true, paramType = "query", dataType = "String")
    })
    public UCResponse get(String url) {
        if (StringUtils.isEmpty(url) || url.length() > CommonConstant.SHORT_URL_LENGTH_LIMIT) {
            return ResponseUtil.builderErrorResponse(CommonConstant.VALIDATION_FAILED_RESPONSE_CODE, "参数长度超出限制");
        }
        String longUrl = converterService.getLongUrl(url);
        return ResponseUtil.buildSuccessResponse(longUrl);
    }

}
