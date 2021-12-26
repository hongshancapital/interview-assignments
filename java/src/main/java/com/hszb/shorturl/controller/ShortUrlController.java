package com.hszb.shorturl.controller;

import com.hszb.shorturl.model.ShortUrlResult;
import com.hszb.shorturl.model.request.QueryLongUrlRequest;
import com.hszb.shorturl.model.request.TransferShortUrlRequest;
import com.hszb.shorturl.model.response.BaseResponse;
import com.hszb.shorturl.service.ShortUrlTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 12:46 下午
 * @Version: 1.0
 * @Description:
 */

@Api("短域名转换服务")
@Controller
@RequestMapping("/shortSerivce")
@Slf4j
public class ShortUrlController {

    @Autowired
    private ShortUrlTransferService shortUrlTransferService;

    @ApiOperation(value = "长域名转短域名")
    @RequestMapping(value = "/transferShortUrl", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<ShortUrlResult>> transferShortUrl (@RequestBody TransferShortUrlRequest request) {
        return shortUrlTransferService.transferShortUrl(request);
    }

    @ApiOperation(value = "查询长域名地址")
    @RequestMapping(value = "/queryLongUrl", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> transferShortUrl (@RequestBody QueryLongUrlRequest request) {
        return shortUrlTransferService.queryLongUrl(request.getShortCode());
    }

}
