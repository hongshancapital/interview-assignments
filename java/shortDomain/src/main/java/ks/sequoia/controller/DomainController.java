package ks.sequoia.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ks.sequoia.aware.CacheServiceAware;
import ks.sequoia.eobj.DomainEObj;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @since 2022-01-09
 * @author jing.tong
 */
@RestController
@Api(value = "domain of class that can support some methods for accessing domain")
public class DomainController {
    @Resource
    private CacheServiceAware domainService;

    @ApiOperation(value = "the method can query long domain", notes = "接受长域名信息，返回短域名信息")
    @GetMapping("/queryEObjByLongDomain")
    public DomainEObj queryEObjByLongDomain(String longDomain){
        if(StringUtils.isEmpty(longDomain)){
            throw new RuntimeException("domain name is not exist");
        }
      return  domainService.queryEObjByLongDomain(longDomain);
    }
    @ApiOperation(value = "the method can query short domain", notes = "接受短域名信息，返回长域名信息")
    @GetMapping("/queryEObjByShortDomain")
    public DomainEObj queryEObjByShortDomain(String shortDomain){
        if(StringUtils.isEmpty(shortDomain)){
            throw new RuntimeException("domain name is not exist");
        }
        return  domainService.queryEObjByShortDomain(shortDomain);
    }
}
