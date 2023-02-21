package com.wg.controller;

import com.wg.common.Result;
import com.wg.common.ResultUtil;
import com.wg.service.DomainService;
import com.wg.vo.DomainVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/domain")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @ApiOperation(value = "获取短域名信息")
    @PostMapping(value = "/getShortUrl")
    public Result<String> getShortUrl(@Validated  @RequestBody DomainVO vo){
        String shortUrl = domainService.getShortUrl(vo.getUrl());
        return ResultUtil.success(shortUrl);
    }

    @ApiOperation(value = "获取长域名信息")
    @PostMapping(value = "/getRealUrl")
    public Result<String> getRealUrl(@Validated @RequestBody DomainVO vo){
        String realUrl = domainService.getRealUrl(vo.getUrl());
        return ResultUtil.success(realUrl);
    }

}
