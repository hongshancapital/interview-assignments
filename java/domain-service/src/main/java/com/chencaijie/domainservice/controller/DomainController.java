package com.chencaijie.domainservice.controller;

import com.chencaijie.domainservice.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "域名服务",description = "域名服务")
@RequestMapping(value = "/domainService")
@RestController
public class DomainController {

    @Autowired
    private DomainService domainService;

    @ApiOperation(value = "短域名读取接口")
    @GetMapping("/getDomainName")
    public String getLongDomainName(@ApiParam(value = "短域名")@RequestParam String shortDomain ) {
        return domainService.getLongDomainName(shortDomain);
    }

    @ApiOperation(value = "短域名存储接口")
    @PostMapping("/saveDomainName")
    public String saveDomainName(@ApiParam(value = "长域名")@RequestParam String domainName) {
        return domainService.saveDomainName(domainName);
    }
}
