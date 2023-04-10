package com.example.baiyang.demo.controller;

import com.example.baiyang.demo.constant.ParamErrorCode;
import com.example.baiyang.demo.constant.SystemErrorCode;
import com.example.baiyang.demo.model.RequestDTO;
import com.example.baiyang.demo.model.ResponseDTO;
import com.example.baiyang.demo.model.Result;
import com.example.baiyang.demo.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/14
 * @description: 短域名服务controller类
 */
@RestController
@RequestMapping("/api")
@Api(description = "短域名服务接口")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "根据长域名获取短域名信息")
    @RequestMapping(value = "/getShortUrl", method = RequestMethod.POST, produces = "application/json")
    public Result getShortUrl(@Validated @RequestBody RequestDTO requestDTO) {
        //参数校验
        if (null == requestDTO || StringUtils.isBlank(requestDTO.getLongUrl())) {
            return Result.newErrorResult(ParamErrorCode.ILLEGAL_PARAM_ERROR.getErrorCode(), ParamErrorCode.ILLEGAL_PARAM_ERROR.getErrorMessage());
        }

        //获取短域名信息
        String shortUrl = shortUrlService.getShortUrl(requestDTO.getLongUrl(), StringUtils.isBlank(requestDTO.getDigest()) ? "MD5" : requestDTO.getDigest());
        if (StringUtils.isBlank(shortUrl)) {
            return Result.newErrorResult(SystemErrorCode.SYSTEM_ERROR.getErrorCode(), SystemErrorCode.SYSTEM_ERROR.getErrorMessage());

        }

        //返回结果
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setLongUrl(requestDTO.getLongUrl());
        responseDTO.setShortUrl(shortUrl);

        return Result.newSuccessResult(responseDTO);
    }

    @ApiOperation(value = "根据短域名获取长域名信息")
    @RequestMapping(value = "/getLongUrl", method = RequestMethod.POST, produces = "application/json")
    public Result getLongUrl(@Validated @RequestBody RequestDTO requestDTO) {
        //参数校验
        if (null == requestDTO || StringUtils.isBlank(requestDTO.getShortUrl())) {
            return Result.newErrorResult(ParamErrorCode.ILLEGAL_PARAM_ERROR.getErrorCode(), ParamErrorCode.ILLEGAL_PARAM_ERROR.getErrorMessage());
        }

        //获取长域名信息
        String longUrl = shortUrlService.getLongUrl(requestDTO.getShortUrl());
        if (StringUtils.isBlank(longUrl)) {
            return Result.newErrorResult(ParamErrorCode.URL_NOT_EXIST_ERROR.getErrorCode(), ParamErrorCode.URL_NOT_EXIST_ERROR.getErrorMessage());

        }

        //返回结果
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setLongUrl(longUrl);
        responseDTO.setShortUrl(requestDTO.getShortUrl());

        return Result.newSuccessResult(responseDTO);
    }
}
