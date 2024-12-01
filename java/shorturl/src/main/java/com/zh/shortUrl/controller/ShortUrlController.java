package com.zh.shortUrl.controller;

import com.zh.shortUrl.common.BaseResponse;
import com.zh.shortUrl.dto.ShortUrlCommonDto;
import com.zh.shortUrl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hang.zhang
 * @Date 2022/1/19 15:41
 * @Version 1.0
 */
@RestController
@Api("短链接相关功能API")
public class ShortUrlController {
    @Autowired
    ShortUrlService shortUrlService;

    /**
     * 根据长链接获取短连接并返回
     * @param requestDto
     * @return
     */
    @ApiOperation("根据长链接获取短连接并返回")
    @RequestMapping(value = "/getShortUrl", produces = "application/json; charset=utf-8", method = {RequestMethod.POST})
    public BaseResponse<String> getShortUrl(@RequestBody ShortUrlCommonDto requestDto){
        return shortUrlService.getShortUrl(requestDto.getLongUrl());
    }

    /**
     * 根据短链接查询长链接
     * @param requestDto
     * @return
     */
    @ApiOperation("根据短链接查询长链接")
    @RequestMapping(value = "/getLongUrl", produces = "application/json; charset=utf-8", method = {RequestMethod.POST})
    public BaseResponse getLongUrl(@RequestBody ShortUrlCommonDto requestDto){
        return shortUrlService.getLongUrl(requestDto.getShortUrl());
    }


    /**
     * swagger测试泛型复杂对象
     * @param requestDto
     * @return
     */
    @ApiOperation("swagger测试泛型复杂对象")
    @RequestMapping(value = "/testSwagger", produces = "application/json; charset=utf-8", method = {RequestMethod.POST})
    public BaseResponse<ShortUrlCommonDto> testSwagger(@RequestBody ShortUrlCommonDto requestDto){
        ShortUrlCommonDto dto = new ShortUrlCommonDto();
        dto.setLongUrl("21131");
        dto.setShortUrl("321321");
        return new BaseResponse(dto);
    }
}
