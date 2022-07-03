package com.assignment.controller;

import com.assignment.api.DomainTransforAPIServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shican.sc
 * @Date: 2022/6/12 23:28
 * @see
 */
@RestController
public class DomainTransforController {

    @Autowired
    private DomainTransforAPIServer domainTransforAPIServer;

    @RequestMapping("toLongDomain")
    public String getLongDomain(@RequestParam("shotUrl") String shotUrl){
        return domainTransforAPIServer.shotToLongDomain(shotUrl);
    }

    @RequestMapping("toShotDomain")
    public String getShotDomain(@RequestParam("longUrl") String longUrl){
        return domainTransforAPIServer.longToShotDomain(longUrl);
    }
}

