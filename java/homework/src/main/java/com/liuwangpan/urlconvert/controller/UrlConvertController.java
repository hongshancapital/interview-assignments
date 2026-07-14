package com.liuwangpan.urlconvert.controller;

import com.liuwangpan.urlconvert.common.UrlConvertException;
import com.liuwangpan.urlconvert.enums.ExceptionEnum;
import com.liuwangpan.urlconvert.model.response.BaseResponse;
import com.liuwangpan.urlconvert.model.request.GetOriginalUrlRequest;
import com.liuwangpan.urlconvert.model.request.UrlConvertRequest;
import com.liuwangpan.urlconvert.services.UrlConvertService;
import com.liuwangpan.urlconvert.utils.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Deacription 短地址请求管理
 * @author wp_li
 **/
@RestController
@RequestMapping("/urlConvertController")
@Slf4j
@Api(tags = {"短地址管理"})
public class UrlConvertController {

    @Autowired
    private UrlConvertService urlConvertService;

    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    @PostMapping(value = "/v1/create", produces = "application/json")
    public BaseResponse<String> create(@RequestBody @Validated UrlConvertRequest urlConvertRequest) {
        if (!UrlUtil.isValidationUrl(urlConvertRequest.getLongUrl())) {
            throw new UrlConvertException(ExceptionEnum.INVALID_URL);
        }
        String shortUrl = urlConvertService.generateShortUrl(urlConvertRequest.getLongUrl());
        return new BaseResponse<>(shortUrl);
    }


    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
    @PostMapping(value = "/v1/getOriginalUrl", produces = "application/json")
    public BaseResponse<String> getOriginalUrl(@RequestBody @Validated GetOriginalUrlRequest originalUrlRequest) {
        if (!UrlUtil.isValidationUrl(originalUrlRequest.getShortUrl())) {
            throw new UrlConvertException(ExceptionEnum.INVALID_URL);
        }
        return new BaseResponse<>(urlConvertService.getLongUrl(originalUrlRequest.getShortUrl()));
    }

}