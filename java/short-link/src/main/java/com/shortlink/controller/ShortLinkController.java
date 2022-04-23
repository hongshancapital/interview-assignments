package com.shortlink.controller;

import com.shortlink.service.ShortLinkServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortlink")
@Api(value = "/ShortLinkController", tags = {"短域名服务API"})
@Slf4j
public class ShortLinkController {
    @Autowired
    private ShortLinkServiceImpl shortLinkService;

    @RequestMapping(value = "/generateShortLink", method = RequestMethod.POST)
    @ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息")
    public String generateShortLink(@RequestBody String longlink){
        return shortLinkService.saveShortLink(longlink);
    }

    @GetMapping(value = "/convert2Longlink")
    @ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息")
    public String convert2Longlink(@RequestParam("shortlink")String shortlink){
        return shortLinkService.convert2LongLink(shortlink);
    }
}
