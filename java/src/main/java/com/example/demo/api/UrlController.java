/**
 * @(#)UrlController.java, 12月 26, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo.api;

import com.example.demo.application.biz.UrlHandleBiz;
import com.example.demo.constant.*;
import com.google.common.base.Preconditions;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@Api(value = "长域名、短域名相互转换接口", protocols = "application/json", produces = "application/json")
@RestController
@RequestMapping(CommonConstant.BASE_API+"/url")
public class UrlController {

    @Resource
    private UrlHandleBiz urlHandleBiz;

    @PutMapping()
    @ApiOperation("将长域名转换短域名")
    public Response<String> transferLongUrl(@RequestBody String longUrl) {
        try {
            String shortUrl = urlHandleBiz.transferLongUrl(longUrl);
            return Response.createSuccess(shortUrl);
        } catch (Exception e) {
            log.error("将长域名转换短域名，longUrl:{}", longUrl, e);
            return Response.createFail(e.getMessage());
        }
    }

    @GetMapping()
    @ApiOperation("依据短域名获取长域名")
    public Response<String> getLongUrl(@RequestParam("shortUrl") String shortUrl) {
        try {
            String longUrl = urlHandleBiz.getLongUrl(shortUrl);
            return Response.createSuccess(longUrl);
        } catch (Exception e) {
            log.error("依据短域名获取长域名异常，shortUrl:{}", shortUrl, e);
            return Response.createFail(e.getMessage());
        }
    }

}