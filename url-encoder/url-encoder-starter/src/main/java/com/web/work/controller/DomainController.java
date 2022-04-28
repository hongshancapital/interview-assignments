package com.web.work.controller;

import com.web.work.common.exception.ResponseWrapper;
import com.web.work.service.DomainService;
import com.web.work.validator.DomainValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * the domain http service
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/26 9:34 PM
 */
@Api("Domain Service Api")
@RestController
public class DomainController {

    private final DomainValidator domainValidator;
    private final DomainService domainService;

    @Autowired
    public DomainController(DomainValidator domainValidator, DomainService domainService) {
        this.domainValidator = domainValidator;
        this.domainService = domainService;
    }

    @ApiOperation("receive full domain and return short domain")
    @RequestMapping(value = "/domain", method = RequestMethod.POST)
    public ResponseWrapper<String> createDomain(@RequestParam("fullUrl") String fullUrl) {
        domainValidator.validateUrl(fullUrl);
        String shortDomain = domainService.createShortDomain(fullUrl);
        return ResponseWrapper.success(shortDomain);
    }

    @ApiOperation("get short domain by full domain")
    @RequestMapping(value = "/domain/{shortUrl}", method = RequestMethod.GET)
    public ResponseWrapper<String> getDomain(@PathVariable("shortUrl") String shortUrl) {
        domainValidator.validateUrl(shortUrl);
        String fullDomain = domainService.getFullDomain(shortUrl);
        return ResponseWrapper.success(fullDomain);
    }

}
