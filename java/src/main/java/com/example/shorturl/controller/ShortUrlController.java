package com.example.shorturl.controller;

import com.example.shorturl.service.core.IShortUrlService;
import com.example.shorturl.service.dto.ShortUrlDto;
import com.example.shorturl.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyp
 * @date 2022/1/16 10:08
 */
@RestController
@Api(value = "短链服务", tags = "短链接服务")
public class ShortUrlController {

    private IShortUrlService shortUrlService;

    ShortUrlController(@Qualifier("randomShotUrlService") IShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/getShortUrl")
    @ApiOperation("根据长链接生成短链接")
    public ResponseResult<ShortUrlDto> getShortLink(@ApiParam(value = "长连接地址", required = true, example = "http://www.baidu.com") String longUrl){
        return ResponseResult.success(shortUrlService.getShortUrl(longUrl));
    }

    @GetMapping("/getLongUrl")
    @ApiOperation("获取短链接接对应的长链接")
    public ResponseResult<String> getLongLink(@ApiParam(value = "短连接地址", required = true, example = "http://tt.qdreT56f") String shortUrl){
        return ResponseResult.success(shortUrlService.getLongUrl(shortUrl));
    }
}
