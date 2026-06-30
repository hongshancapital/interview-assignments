package com.example.domain.controller;

import com.example.domain.service.DomainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @title: DomainController
 * @Author DH
 * @Date: 2021/12/6 18:17
 * 长短域名解析
 */
@RestController("/domain")
public class DomainController {


    @Resource
    private DomainService domainService;


    @GetMapping("/getShortDomain")
    @ApiOperation("根据长域名兑换短域名,并存储映射关系")
    public String getShortDomain(@RequestParam("domain") String domain) {
        return domainService.addDomainInfo(domain);
    }

    @GetMapping("/getDomain")
    @ApiOperation("根据短域名查询长域名")
    public String getDomain(@RequestParam("shortDomain") String shortDomain) {
        return domainService.getDomain8ShortDomain(shortDomain);
    }


}
