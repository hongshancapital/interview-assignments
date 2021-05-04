package com.sequoiacap.sdn.controller;

import com.sequoiacap.sdn.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : fanzhaofei
 * @date : 2021/5/4 21:27
 * @description: 短域名接口
 */
@RestController
@RequestMapping("/api/domain")
@Api(tags = "短域名接口")
public class DomainController {
    @Resource
    private DomainService domainService;

    @GetMapping("/saveLang")
    @ApiOperation("短域名存储")
    @ApiImplicitParam(name = "longDomain", value = "长域名", paramType = "query", dataType = "String", required = true)
    public String save(String longDomain) {
        return domainService.langToShort(longDomain);
    }

    @GetMapping("/findByShort")
    @ApiOperation("短域名读取")
    @ApiImplicitParam(name = "shortDomain", value = "短域名", paramType = "query", dataType = "String", required = true)
    public String find(String shortDomain) {
        return domainService.getLang(shortDomain);
    }
}
