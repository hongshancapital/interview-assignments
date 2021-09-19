package com.example.domain.controller;

import com.example.domain.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import static com.example.domain.util.Constant.CONTAINER_LASTING;

/**
 * 短域名服务服务API
 * Created by shuWei.yu
 * on 2021年9月19日13:38:48
 *
 */
@RestController
@RequestMapping("/domain")
@Api(tags = "域名服务")
public class DomainController {

    @Autowired
    DomainService domainService;

    @PostMapping("/saveDomain")
    @ApiOperation(value = "短域名存储接口" ,notes = "接受长域名信息，返回短域名信息")
    public String saveDomain(@RequestParam("longDomain") String longDomain){
        return domainService.saveDomainMethod(longDomain);
    }

    @GetMapping("/getDomain")
    @ApiOperation(value = "短域名读取接口" ,notes = "接受短域名信息，返回长域名信息")
    @Cacheable(value = CONTAINER_LASTING,key = "#shortDomain")
    public String getTealDomain(@RequestParam("shortDomain") String shortDomain){
        return "啊呀！没有你要的链接:)";
    }

}