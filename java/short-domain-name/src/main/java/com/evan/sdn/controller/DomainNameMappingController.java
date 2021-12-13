package com.evan.sdn.controller;

import com.evan.sdn.service.DomainNameMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyuwen
 * @date 2021/12/13
 */
@Api(tags = "域名映射")
@RestController
@RequiredArgsConstructor
public class DomainNameMappingController {

    private final DomainNameMappingService domainNameMappingService;

    @ApiOperation("短域名存储")
    @PostMapping("create")
    public String create(String longDomainName){
        if(StringUtils.isEmpty(longDomainName)){
            return null;
        }
        return domainNameMappingService.save(longDomainName);
    }

    @ApiOperation("短域名读取")
    @GetMapping("query")
    public String query(String shortDomainName){
        if(StringUtils.isEmpty(shortDomainName)){
            return null;
        }
        return domainNameMappingService.query(shortDomainName);
    }
}
