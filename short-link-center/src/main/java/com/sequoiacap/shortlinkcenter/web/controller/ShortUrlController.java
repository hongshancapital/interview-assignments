package com.sequoiacap.shortlinkcenter.web.controller;

import com.sequoiacap.shortlinkcenter.application.business.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
@Api(tags = "短链接服务")
@RestController
@RequestMapping("short")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    private static final String TARGET_DOMAIN_NAME_DEFAULT = "h.cn";

    /**
     * 根据url获取短链接
     * @return 短链接
     */
    @ApiOperation("根据url获取短链接")
    @GetMapping("/queryShortUrl")//
    public String queryShortUrl(@ApiParam("长链接") @RequestParam("url") String url) {
        // 实际项目需要做用户解析和校验（也可以增加api-gateway、sentinel限流、降级）
        return shortUrlService.getBySourceUrl(url, TARGET_DOMAIN_NAME_DEFAULT);
    }

    /**
     * 根据shortUrl获取长链接
     * @return 长链接
     */
    @ApiOperation("根据shortUrl获取长链接")
    @GetMapping("/queryUrl")
    public String querySourceUrl(@ApiParam("短链链接") @RequestParam("shortUrl") String shortUrl) {
        // 实际项目需要做用户解析和校验（也可以增加api-gateway、sentinel限流、降级）
        return shortUrlService.getByShortUrl(shortUrl);
    }

}
