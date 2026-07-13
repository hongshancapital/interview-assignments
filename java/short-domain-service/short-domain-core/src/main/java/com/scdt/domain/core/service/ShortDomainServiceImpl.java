package com.scdt.domain.core.service;

import com.scdt.domain.api.ShortDomainService;
import com.scdt.domain.api.common.Response;
import com.scdt.domain.core.manager.ShortDomainManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tuxiaozhou
 * @date 2022/4/2
 */
@Api("短域名接口")
@RestController
@RequestMapping("/api")
public class ShortDomainServiceImpl implements ShortDomainService {

    @Resource
    private ShortDomainManager shortDomainManager;

    @Override
    @PostMapping("/short-url")
    @ApiOperation("短域名存储接口")
    public Response<String> createShortUrl(String longUrl) {
        return shortDomainManager.createShortUrl(longUrl);
    }

    @Override
    @GetMapping("/long-url/{shortUri}")
    @ApiOperation("短域名读取接口")
    public Response<String> getLongUrl(@PathVariable("shortUri") String shortUri) {
        if (shortUri.startsWith("http://") || shortUri.startsWith("https://")) {
            shortUri = shortUri.substring(shortUri.lastIndexOf("/") + 1);
        }
        return shortDomainManager.getLongUrl(shortUri);
    }

}
