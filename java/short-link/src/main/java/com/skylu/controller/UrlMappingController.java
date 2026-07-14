package com.skylu.controller;

import com.skylu.service.UrlMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName UrlMappingController.java
 * @Description 域名服务接口
 * @createTime 2022年04月22日 17:02:00
 */
@Slf4j
@Api(tags = "域名服务接口")
@RequestMapping("url")
@RestController
public class UrlMappingController extends BaseController {

    private final UrlMappingService urlMappingService;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @ApiOperation("短域名存储接口")
    @ApiImplicitParam(name = "url", value = "长域名", paramType = "form")
    @PostMapping("/shortStore")
    public ResponseEntity<RT> shortStore(@RequestParam("url") String longUrl) {
        return this.buildResponse(urlMappingService.longToShort(longUrl));
    }

    @ApiOperation("短域名读取接口")
    @ApiImplicitParam(name = "code", value = "短域名编码", paramType = "form")
    @PostMapping("/getLongUrl")
    public ResponseEntity<RT> getLongUrl(@RequestParam("code") String shortUrl) {
        return this.buildResponse(urlMappingService.shortToLong(shortUrl));
    }


}
