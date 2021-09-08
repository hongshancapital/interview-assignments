package com.scdt.url.controller;

import com.scdt.url.service.IShortUrlService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "ShortUrl", description = "短域名", tags = "ShortUrl")
public class ShortUrlController {

    @Autowired
    IShortUrlService IShortUrlService;

    /**
     * 根据长域名生成短域名
     *
     * @param longUrl
     * @return
     */
    @PostMapping("/create")
    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息", httpMethod = "POST")
    public String shortUrlCreate(@ApiParam(name="长域名", value="传入长域名信息", required=true) @RequestBody String longUrl) {
        return IShortUrlService.shortUrlCreate(longUrl);
    }

    /**
     * 根据短域名查找长域名
     * 
     * @param shortUrl
     * @return
     */
    @GetMapping("/find")
    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name="shortUrl", dataType = "string", paramType = "query", required=true)})
    public String longUrlFind(@RequestParam("shortUrl") String shortUrl) {
        return IShortUrlService.longUrlFind(shortUrl);
    }
}
