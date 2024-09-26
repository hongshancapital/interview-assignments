package com.scdt.china.shortdomain.controller;

import com.scdt.china.shortdomain.service.DomainService;
import com.scdt.china.shortdomain.utils.Result;
import com.scdt.china.shortdomain.utils.ReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CZ
 * @Date: 2022/1/22 12:16
 * @Description:
 */
@RestController
@Slf4j
@Api("短链接服务")
public class DomainController {
    @Autowired
    private DomainService domainService;

    @ApiOperation(value = "Hello World！！！", notes = "")
    @GetMapping(value = "/hello")
    public String hello(@ApiParam(value = "用户名", required = true) @RequestParam String name) {
        return "Hello " + name + "!";
    }

    @ApiOperation(value = "长域名转换为短域名", notes = "")
    @GetMapping(value = "/getShortDomain")
    public Result<String> getShortDomain(@ApiParam(value = "长链接", required = true) @RequestParam String longDomain) {
        try {
            if (StringUtils.isBlank(longDomain)) {
                return Result.fail(ReturnCode.PARAM_ERROR);
            }
            log.debug("getShortDomain longDomain={}", longDomain);
            return domainService.getShortDomain(longDomain);
        } catch (Exception e) {
            log.error("longDomain={}, msg={}", longDomain, e.getMessage(), e);
            return Result.fail(ReturnCode.FAILED.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "短域名转换为长域名", notes = "")
    @GetMapping(value = "/getLongDomain")
    public Result<String> getLongDomain(@ApiParam(value = "短链接", required = true) @RequestParam String shortDomain) {
        try {
            if (StringUtils.isBlank(shortDomain)) {
                return Result.fail(ReturnCode.PARAM_ERROR);
            }
            return domainService.getLongDomain(shortDomain);
        } catch (Exception e) {
            log.error("shortDomain={}, msg={}", shortDomain, e.getMessage(), e);
            return Result.fail(ReturnCode.FAILED.getCode(), e.getMessage());
        }
    }

}
