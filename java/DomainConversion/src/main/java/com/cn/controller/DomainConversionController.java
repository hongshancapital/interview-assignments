package com.cn.controller;

import com.cn.service.DomainConversionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wukui
 * @date 2021--12--29
 **/
@RestController
@Api(tags = "域名转换接口")
@RequestMapping("/domain")
public class DomainConversionController {
    @Autowired
    public DomainConversionService domainConversionService;

    @PostMapping("/getShortDomain")
    @ApiOperation("长域名转短域名")
    @ApiImplicitParams({@ApiImplicitParam(name="rootDomain",value = "目标域名"),@ApiImplicitParam(name="longDomain",value = "需要转换的URL")})
    public String builderShortDomain(String rootDomain,String longDomain) {
        return domainConversionService.builderShortDomain(rootDomain,longDomain);
    }

    @GetMapping("/findLongDomain")
    @ApiOperation("查询短域名对应长域名")
    @ApiImplicitParams({@ApiImplicitParam(name="shortDomain",value = "需要转换为原始域名的短URL")})
    public String findLongDomain(String shortDomain) {
        return domainConversionService.findLongDomain(shortDomain);
    }


}
