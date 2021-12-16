package com.scdt.china.shorturl.controller;

import com.scdt.china.shorturl.configuration.ApplicationProperties;
import com.scdt.china.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 短地址服务API
 *
 * @author ng-life
 */
@RestController
@Api(tags = "短域名API")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    private final ApplicationProperties applicationProperties;

    public ShortUrlController(ShortUrlService shortUrlService,
                              ApplicationProperties shortUrlProperties) {
        this.shortUrlService = shortUrlService;
        this.applicationProperties = shortUrlProperties;
    }

    /**
     * 展开短地址
     *
     * @param shortCode 地址短码
     * @return 长地址
     */
    @ApiOperation(value = "展开短地址")
    @GetMapping("/{shortCode}")
    public ResponseEntity<String> expandShortUrl(
            @ApiParam(value = "短码", required = true, example = "12345678")
            @PathVariable("shortCode") String shortCode) {
        String fullUrl = shortUrlService.expand(shortCode);
        if (fullUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .lastModified(applicationProperties.getBaseTime())
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(fullUrl);
    }

    /**
     * 长地址生成短地址
     *
     * @param fullUrl 长地址
     * @return 短地址
     */
    @ApiOperation(value = "生成短地址")
    @PostMapping("/")
    public String generateShortUrl(@RequestBody String fullUrl) {
        return applicationProperties.getBaseUrl() + shortUrlService.generate(fullUrl);
    }

}
