package com.example.demo.domainnameservice.controller;

import com.example.demo.domainnameservice.entity.ApiResult;
import com.example.demo.domainnameservice.service.DomainNameService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * domain name controller.
 * designed for transforming between short and long domain names.
 *
 * @author laurent
 * @date   2021-12-11 上午10:04
 */
@RestController
@RequestMapping(value = DomainNameController.DOMAIN_NAME_REQUEST_PREFIX)
public class DomainNameController {

    public static final String DOMAIN_NAME_REQUEST_PREFIX = "domain/v1/";

    private final DomainNameService domainNameService;

    @Autowired
    public DomainNameController(final DomainNameService domainNameService) {
        this.domainNameService = domainNameService;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "long url", dataType = "String", defaultValue = "www.baidu.com")
    })
    @GetMapping(value = "store")
    public ApiResult<String> storeLongDomainName(@RequestParam(name = "url") String url) {
        return new ApiResult<>(domainNameService.storeUrl(url));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "short url", dataType = "String", defaultValue = "")
    })
    @GetMapping(value = "visit")
    public ApiResult<String> readShortDomainName(@RequestParam(name = "url") String url) {
        return new ApiResult<>(domainNameService.visitUrl(url));
    }

}
