package com.skg.domain.demo.controller;

import com.skg.domain.demo.anno.ApiVersion;
import com.skg.domain.demo.base.Result;
import com.skg.domain.demo.param.LongDomainParam;
import com.skg.domain.demo.param.ShortDomainParam;
import com.skg.domain.demo.service.domain.IDomainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author smith skg
 * @Date 2021/10/11 16:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
@Validated
public class DomainController {

    @Autowired
    private IDomainService domainService;

    @PostMapping("/{version}/get-short-domain")
    @ApiOperation(value = "获取短域名", notes = "长域名-->短域名")
    @ApiVersion("1.1")
    public Result<?> getShortDomain(@RequestBody @Valid LongDomainParam longDomainParam) {
        return Result.data(domainService.generateShort(longDomainParam.getLongDomain()));
    }

    @PostMapping("/{version}/get-short-domain")
    @ApiOperation(value = "获取短域名", notes = "长域名-->短域名")
    @ApiVersion("1.2")
    public Result<?> getShortDomainByVersion(@RequestBody LongDomainParam longDomainParam) {
        return Result.data("000000");
    }

    @PostMapping("/get-long-domain")
    @ApiOperation(value = "获取长域名", notes = "短域名-->长域名")
    public Result<?> getLongDomain(@RequestBody @Valid ShortDomainParam shortDomainParam) {
        return Result.data(domainService.getLongByShortDomain(shortDomainParam.getShortDomain()));
    }

}
