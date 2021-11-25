package net.sky.demo.url.mapping.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sky.demo.url.mapping.bean.GenerateShortUrlRequest;
import net.sky.demo.url.mapping.service.UrlMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/url_mapping")
public class UrlMappingController {

    @Resource
    UrlMappingService urlMappingService;


    @PostMapping("/long2short")
    @ApiOperation(value = "长域名生成短域名", notes = "长域名生成短域名")
    public ResponseEntity<String> long2short(@RequestBody GenerateShortUrlRequest request) {
        return ResponseEntity.ok(urlMappingService.generateShortUrl(request.getUrl()));
    }


    @GetMapping("/short2long/{shorUrl}")
    @ApiOperation(value = "短域名查询对应长域名", notes = "短域名查询对应长域名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shorUrl", value = "短域名", required = true),
    })
    public ResponseEntity<String> short2long(@PathVariable(value = "shorUrl", required = true) String shorUrl) {
        return ResponseEntity.ok(urlMappingService.querySourceUrl(shorUrl));
    }

    public UrlMappingService getUrlMappingService() {
        return urlMappingService;
    }

    public void setUrlMappingService(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }
}
