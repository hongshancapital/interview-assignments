package com.example.assignment.controller;

import com.example.assignment.Exception.ShortCodeUseOutException;
import com.example.assignment.common.dto.ResponseEnum;
import com.example.assignment.common.dto.Result;
import com.example.assignment.service.ShortUrlService;
import com.example.assignment.utils.MatchUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("短域名服务")
@RestController
@Slf4j
@RequestMapping("api/short-url")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "接受长域名信息，返回短域名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "originalUrl", value = "长域名", required = true, paramType = "query")
    })
    @PostMapping("/generate")
    public Result generate(@RequestParam("originalUrl") String originalUrl) throws ShortCodeUseOutException {
        Result<String> result = new Result<>();
        if (originalUrl.length() > 2083 || !MatchUtil.isUrlLegal(originalUrl)) {
            return result.failed(ResponseEnum.ILLEGAL_URL);
        }
        return result.success(shortUrlService.generate(originalUrl));
    }

    @ApiOperation(value = "接受短域名信息，返回长域名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短域名", required = true, paramType = "query")
    })
    @GetMapping("/parse")
    public Result parse(@RequestParam("shortUrl") String shortUrl) {
        Result<String> result = new Result<>();
        if (!MatchUtil.isShortCodeLegal(shortUrl)) {
            return result.failed(ResponseEnum.ILLEGAL_SHORT_CODE);
        }
        return result.success(shortUrlService.parse(shortUrl));
    }
}
