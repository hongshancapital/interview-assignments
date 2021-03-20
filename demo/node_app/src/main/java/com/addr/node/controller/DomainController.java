package com.addr.node.controller;

import com.addr.node.service.DomainService;
import com.addr.node.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DomainController {

    @Autowired
    private DomainService domainService;

    @RequestMapping("/api/shortDomain")
    public String shortDomain(@RequestParam(required=false) String longDomain) {
        Map map = new HashMap();
        String sd = CommonUtils.shorten(longDomain);
        map.put("longDomain", longDomain);
        map.put("shortDomain", sd);
        int num = domainService.insertDomain(map);
        if (num > 0)
            return sd;
        return null;
    }

    @RequestMapping("/api/longDomain")
    public String longDomain(@RequestParam(required=false) String shortDoamin) {
        return domainService.queryDomain(shortDoamin);
    }
}
