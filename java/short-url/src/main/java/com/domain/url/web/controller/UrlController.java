package com.domain.url.web.controller;

import com.domain.url.exception.ServiceException;
import com.domain.url.service.UrlService;
import com.domain.url.web.data.JsonResult;
import com.domain.url.web.data.UrlReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接服务 Controller
 */
@RestController
@RequestMapping("api/url")
@Api("短链接服务controller")
public class UrlController {
    @Autowired
    private UrlService urlService;

    /**
     * 短链接存储接口：接受长链接信息，返回短链接信息
     */
    @ApiOperation("短链接存储接口：接受长链接信息，返回短链接信息")
    @PostMapping("shorten")
    public JsonResult<?> shorten(@RequestBody UrlReq req) throws ServiceException {
        return JsonResult.ok(this.urlService.shorten(req));
    }


    /**
     * 短链接读取接口：接受短链接信息，返回长链接信息
     */
    @ApiOperation("短链接读取接口：接受短链接信息，返回长链接信息")
    @ApiImplicitParam(name = "shortUrl", value = "短链接", required = true, dataTypeClass = String.class)
    @GetMapping("original")
    public JsonResult<?> original(@RequestParam String shortUrl) throws ServiceException {
        return JsonResult.ok(this.urlService.original(shortUrl));
    }
}
