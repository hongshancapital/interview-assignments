package com.sequoiacap.tinyurl.controller;

import com.sequoiacap.tinyurl.service.TinyUrlService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "短网址")
@RestController
@RequestMapping("/tinyurl")
public class TinyUrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TinyUrlController.class);
    @Autowired
    private TinyUrlService tinyUrlService;

    @PostMapping
    @ApiOperation("创建短网址")
    @ApiResponses({
            @ApiResponse(code = 201, message = "返回短网址标识"),
            @ApiResponse(code = 400, message = "参数不合法。url须符合规范"),
            @ApiResponse(code = 503, message = "服务器限流")
    })
    public String createTinyUrl(@ApiParam("原始网址") @RequestBody String url) {
        LOGGER.info("url: {}", url);
        return tinyUrlService.createTinyUrl(url);
    }

    @ApiOperation("获取原始网址")
    @GetMapping("/{tinyUrl}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回原始网址"),
            @ApiResponse(code = 400, message = "参数不合法。短网址必须是8位且为数字及大小字母组合"),
            @ApiResponse(code = 404, message = "通过短网址查找不到原始网址"),
            @ApiResponse(code = 503, message = "服务器限流")
    })
    public String getUrl(@ApiParam("短网址标识") @PathVariable(name = "tinyUrl") String tinyUrl) {
        return tinyUrlService.getUrl(tinyUrl);
    }
}
