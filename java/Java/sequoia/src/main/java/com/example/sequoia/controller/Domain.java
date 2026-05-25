package com.example.sequoia.controller;

import com.example.sequoia.service.DomainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xurui
 */
@RestController
@RequestMapping("/api")
public class Domain {
    private DomainService service;

    @RequestMapping(value = "/shortLink")
    @ApiOperation(value = "获取长链接对应的短链接", response = String.class, httpMethod = "GET")
    public String shortLink(String longLink) {
        return service.getShortLink(longLink);
    }

    @RequestMapping(value = "/longLink")
    @ApiOperation(value = "获取短链接对应的长链接", response = String.class, httpMethod = "GET")
    public String longLink(String shortLink) {
        return service.getLongLink(shortLink);
    }

    @Autowired
    public void setService(DomainService service) {
        this.service = service;
    }
}
