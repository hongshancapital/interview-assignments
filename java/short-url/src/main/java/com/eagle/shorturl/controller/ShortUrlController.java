package com.eagle.shorturl.controller;

import com.eagle.shorturl.param.LongUrlParam;
import com.eagle.shorturl.result.Result;
import com.eagle.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author eagle
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/shortUrl")
@Api(tags = "短域名API")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "短域名存储接口")
    public Result<String> add(@RequestBody @Valid LongUrlParam param) {
        return new Result<String>().success(shortUrlService.add(param.getLongUrl()));
    }

    @GetMapping(value = "/get")
    @ApiOperation(value = "短域名读取接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "shortUrl", required = true)})
    public Result<String> get(@RequestParam(name = "shortUrl") String shortUrl) {
        return new Result<String>().success(shortUrlService.get(shortUrl));
    }

}
