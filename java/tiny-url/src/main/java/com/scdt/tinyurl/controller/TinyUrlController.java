package com.scdt.tinyurl.controller;


import com.scdt.tinyurl.annotation.Limiter;
import com.scdt.tinyurl.common.ResponseResult;
import com.scdt.tinyurl.model.UrlEntity;
import com.scdt.tinyurl.service.TinyUrlService;
import com.scdt.tinyurl.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;


@RestController
@Api(value = "短域名生成服务")
public class TinyUrlController {

    @Autowired
    private TinyUrlService tinyUrlService;


    @ApiOperation(value="获取短域名地址",notes = "根据长域名获取短域名地址")
    @PostMapping("/fetchTinyUrl")
    public ResponseResult fetchTinyUrl(@RequestBody @Validated UrlEntity urlEntity) {
        String tinyUrl = tinyUrlService.fetchTinyUrl(urlEntity);
        return ResultUtil.success(tinyUrl, "生成短域名成功");
    }

    @ApiOperation(value="获取长域名地址",notes = "根据短域名获取长域名地址")
    @GetMapping("/fetchLongUrl")
    @Limiter(name = "根据短域名获取长域名地址",accessRate = 1.0)
    public ResponseResult fetchLongUrl(@RequestParam @Pattern(regexp = "^[a-z0-9A-Z]+$") String tinyUrl) {
        String longUrl = tinyUrlService.fetchLongUrl(tinyUrl);
        return ResultUtil.success(longUrl, "获取长域名成功");
    }
}
