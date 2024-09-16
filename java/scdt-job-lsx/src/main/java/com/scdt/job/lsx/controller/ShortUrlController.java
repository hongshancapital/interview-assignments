package com.scdt.job.lsx.controller;

import com.google.common.base.Strings;
import com.scdt.job.lsx.enums.ErrorCodeEnum;
import com.scdt.job.lsx.model.ResponseResult;
import com.scdt.job.lsx.service.IShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author lsx
 */
@RestController
@Api(tags = "短域名服务")
@RequestMapping("/url")
public class ShortUrlController {

    IShortUrlService shortUrlService;

    public ShortUrlController(IShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping("/nativeToShort")
    @ApiOperation(value = "根据原生地址信息生成短地址", notes = "根据原生地址信息生成短地址")
    public ResponseResult<String> nativeToShort(@RequestParam String nativeUrl) {
        String shortUrl = shortUrlService.nativeUrlToShort(nativeUrl);
        return ResponseResult.buildSuccess(shortUrl);
    }


    @GetMapping("/shortToNative")
    @ApiOperation(value = "根据短地址信息返回原生地址", notes = "根据短地址信息返回原生地址")
    public ResponseResult<String> shortToNative(@RequestParam String shortUrl) {
        String nativeUrl = shortUrlService.shortUrlToNative(shortUrl);
        if (org.apache.logging.log4j.util.Strings.EMPTY.equals(nativeUrl)) {
            ResponseResult.buildFail(nativeUrl, ErrorCodeEnum.InvalidParams);
        }
        return ResponseResult.buildSuccess(nativeUrl);
    }

}
