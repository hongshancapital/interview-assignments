package com.chencaijie.domainservice.controller;

import com.chencaijie.domainservice.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DomainController {

    @Autowired
    private DomainService domainService;

    @GetMapping("/getDomainName")
    public String getLongDomainName(@RequestParam String shortDomain) {
        return domainService.getLongDomainName(shortDomain);
    }

    @PostMapping("/saveDomainName")
    public String saveDomainName(@RequestParam String domainName) {
        return domainService.saveDomainName(domainName);
    }
}
