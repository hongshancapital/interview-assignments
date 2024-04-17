package com.example.urltransform.controller;

import com.example.urltransform.service.UrlTransformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * author  fengguangwu
 * createTime  2022/5/7
 * desc
 **/
@RestController
@RequestMapping("/web/url")
@Slf4j
@Api(tags = "短域名服务")
public class UrlTransformController {
    
    @Autowired
    private UrlTransformService transformService;
    
    @ApiOperation("接收短url，返回原始长url")
    @GetMapping
    public String getOriginalUrl(@RequestParam("shortUrl") @NotBlank String shortUrl){
        return transformService.getOriginalUrl(shortUrl);
    }
    
    @ApiOperation("接收原始长url，返回短url")
    @PostMapping
    public String saveOriginalUrl(@RequestParam("longUrl") @NotBlank String longUrl){
        return transformService.saveOriginalUrl(longUrl);
    }
}
