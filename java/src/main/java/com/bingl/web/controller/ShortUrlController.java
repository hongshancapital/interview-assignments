package com.bingl.web.controller;


import com.bingl.web.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "短域名服务")
@RestController
@RequestMapping("/api")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "短域名存储接口")
    @GetMapping(value = "/saveShortUrl/{longUrl}")
    public String saveShortUrl(@ApiParam(value = "长域名", required = true)  @PathVariable(value = "longUrl") String longUrl) {
        String shortUrl=shortUrlService.saveShortUrl(longUrl);
        return shortUrl;
    }

    @ApiOperation(value = "短域名读取接口")
    @GetMapping(value = "/readLongUrl/{shortUrl}")
    public String readLongUrl(@ApiParam(value = "短域名", required = true)  @PathVariable(value = "shortUrl") String shortUrl) {
        try{
            String longUrl=shortUrlService.readLongUrl(shortUrl);
            return longUrl;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}
