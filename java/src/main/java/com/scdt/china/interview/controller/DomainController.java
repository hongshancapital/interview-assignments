package com.scdt.china.interview.controller;

import com.scdt.china.interview.cache.DomainCache;
import com.scdt.china.interview.enm.ResultEnum;
import com.scdt.china.interview.manager.DomainManager;
import com.scdt.china.interview.util.Result;
import com.scdt.china.interview.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sujiale
 */
@RestController
@RequestMapping("/domain")
@Slf4j
@Api(tags = "域名服务Controller")
public class DomainController {
    @ApiOperation(value = "获取短域名接口", notes = "通过长域名，获取短域名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longDomain", value = "长域名", required = true,
                    dataTypeClass = String.class),
    })
    @GetMapping("/getShortDomain")
    public Result<String> getShortDomain(@RequestParam String longDomain) {
        final String s = DomainCache.LONG_SHORT.get(longDomain);
        if (s == null) {
            return ResultUtil.success(
                    DomainManager.getAndSetShortDomain(longDomain));
        } else {
            return ResultUtil.success(s);
        }
    }

    @ApiOperation(value = "获取长域名接口", notes = "短域名通过，获取长域名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortDomain", value = "短域名", required = true,
                    dataTypeClass = String.class)
    })
    @GetMapping("/getLongDomain")
    public Result<String> getLongDomain(@RequestParam String shortDomain) {
        if (shortDomain.length() > DomainManager.SHORT_DOMAIN_MAX_LENGTH) {
            log.warn("DomainController.getLongDomain param[shortDomain] max size is 8：[{}] ",
                    shortDomain);
            return ResultUtil.error(ResultEnum.E000001);
        }
        return ResultUtil.success(DomainCache.SHORT_LONG.get(shortDomain));
    }
}
