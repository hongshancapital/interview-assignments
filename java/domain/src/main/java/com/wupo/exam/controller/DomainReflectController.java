package com.wupo.exam.controller;

import com.wupo.exam.facade.DomainReflectFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 域名映射web接口
 * @author wupo
 * @date 2022-03-13
 */
@Api(tags = "域名映射")
@RestController
@RequestMapping("/reflect")
public class DomainReflectController {

    @Autowired
    private DomainReflectFacade domainReflectFacade;

    @ApiOperation(value = "长域名存储")
    @PostMapping("/long")
    public String saveLongDomain(@ApiParam(required = true, value = "长域名")
                                 @RequestParam String url){
        return this.domainReflectFacade.saveLongDomain(url);
    }

    @ApiOperation(value = "短域名读取")
    @GetMapping("/short")
    public String readShortDomain(@ApiParam(required = true, value = "短域名")
                                  @RequestParam String url){
        return this.domainReflectFacade.getLongDomain(url);
    }
}
