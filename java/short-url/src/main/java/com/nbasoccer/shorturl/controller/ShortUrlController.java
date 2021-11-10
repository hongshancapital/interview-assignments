package com.nbasoccer.shorturl.controller;

import com.nbasoccer.shorturl.service.IShortUrlService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags="短连接服务")
@RestController()
@RequestMapping("/api")
public class ShortUrlController {

    private final IShortUrlService shortUrlService;

    @Autowired
    public ShortUrlController(IShortUrlService shortUrlService){
        this.shortUrlService = shortUrlService;
    }

    @ApiOperation(value="生成短链接地址", notes="生成短链接地址")
    @ApiImplicitParams(@ApiImplicitParam(name = "longUrl", value = "源连接地址", dataType = "String"))
    @GetMapping("/create")
    public Object create(@RequestParam(name="longUrl", required = false) String longUrl){
        Map<String, Object> map = new HashMap<>();
        map.put("shortUrl", shortUrlService.convertShortUrl(longUrl));
        map.put("longUrl", longUrl);
        return map;
    }

    @ApiOperation(value="获取源连接地址", notes="获取源连接地址")
    @ApiImplicitParams(@ApiImplicitParam(name = "shortUrl", value = "短连接地址", dataType = "String"))
    @GetMapping("/query")
    public Object query(@RequestParam(name="shortUrl", required = false) String shortUrl){
        Map<String, Object> map = new HashMap<>();
        map.put("longUrl", shortUrlService.convertLongUrl(shortUrl));
        map.put("shortUrl", shortUrl);
        return map;
    }
}
