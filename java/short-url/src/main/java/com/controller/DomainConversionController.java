package com.controller;

import com.service.DomainConversionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/domainConverison")
@Api(tags = {"短域名服务"}, description = "短域名长域名转换服务")
public class DomainConversionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainConversionController.class);

    @Autowired
    private DomainConversionService domainConversionService;

    /**
     * 功能描述: 长域名获取短域名
     * @param longUrl
     * @return java.lang.Object
     * @author liangjiangwei
     * @throws
     * 2021/10/10
     */
    @GetMapping("/getShortDomainUrl")
    @ApiOperation("获取短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String", paramType ="query", required = true)
    @ApiResponse(code = 200, message = "获取成功")
    public Object getShortDomainUrl(@RequestParam String longUrl) throws Exception {
        return domainConversionService.getShortDomainUrl(longUrl);
    }

    /**
     * 功能描述: 短域名获取长域名
     * @param shortUrl
     * @return java.lang.Object
     * @author liangjiangwei
     * @throws
     * 2021/10/10
     */
    @ApiOperation(value = "通过短域名获取长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String", required = true)
    @ApiResponse(code = 200, message = "获取成功")
    @GetMapping(value = "getLongDomainUrl")
    public Object getLongDomainUrl(@RequestParam String shortUrl) throws Exception {
            return domainConversionService.getLongDomainUrl(shortUrl);
    }
}
