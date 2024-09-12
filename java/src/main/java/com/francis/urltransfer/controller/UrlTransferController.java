package com.francis.urltransfer.controller;

import com.francis.urltransfer.common.Result;
import com.francis.urltransfer.service.ShortUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Francis
 * @Description: url域名转换控制器
 * @date 2022/1/27 16:47
 */
@Tag(name = "短域名服务接口")
@RestController(value = "/url")
public class UrlTransferController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping(value = "/test/ok")
    @Operation(summary = "测试服务是否正常")
    public Result<Object> testResultOk() {
        return Result.ok();
    }

    @GetMapping(value = "/test/warning")
    @Operation(summary = "测试服务是否正常")
    public Result<Object> testResultWarning() {
        return Result.warning("提示信息");
    }

    @GetMapping(value = "/shorter/add")
    @Operation(summary = "短域名存储接口")
    public Result<String> addUrlShorter(@Parameter(description = "长域名") @RequestParam String url) {
        String shortUrl = shortUrlService.shorterUrl(url);
        return Result.ok(shortUrl);
    }

    @GetMapping(value = "/shorter/get")
    @Operation(summary = "短域名读取接口")
    public Result<String> getOriginUrl(@Parameter(description = "短域名") @RequestParam String shortUrl) {
        String url = shortUrlService.getOriginUrl(shortUrl);
        return Result.ok(url);
    }

}
