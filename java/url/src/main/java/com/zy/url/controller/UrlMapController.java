package com.zy.url.controller;

import com.zy.url.common.BaseController;
import com.zy.url.common.ResultVo;
import com.zy.url.dto.UrlMapDto;
import com.zy.url.service.UrlMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(tags = {"短链接接口"})
@RequestMapping("/urlMap")
public class UrlMapController extends BaseController {
    @Autowired
    private UrlMapService urlMapService;

    @ApiOperation("生成短链接接口")
    @PostMapping(path = "/createShortUrl")
    public ResultVo<UrlMapDto> createShortUrl(@RequestBody UrlMapDto urlMapDto) {
        log.info("开始生成短链接");
        return ResultVo.ok(urlMapService.createShortUrl(urlMapDto));
    }

    @ApiOperation("还原成原始URL接口")
    @PostMapping(path = "/restoreUrl")
    public ResultVo<UrlMapDto> restoreUrl(@RequestBody UrlMapDto urlMapDto) {
        log.info("开始还原短链接");
        return ResultVo.ok(urlMapService.restoreUrl(urlMapDto));
    }
}