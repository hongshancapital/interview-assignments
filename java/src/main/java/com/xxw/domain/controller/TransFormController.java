package com.xxw.domain.controller;

import com.xxw.domain.service.LongUrlService;
import com.xxw.domain.service.ShortUrlService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@ApiModel("长域名转短域名并存储处理控制类")
@Controller
@RequestMapping("/domain")
public class TransFormController {

    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private LongUrlService longUrlService;

    @ResponseBody
    @ApiOperation("接受长域名信息，返回短域名信息")
    @PostMapping("/short")
    public String shortUrl(@RequestParam("url") String url){
        return shortUrlService.shortUrl(url);
    }

    @ResponseBody
    @ApiOperation("接受短域名信息，返回长域名信息")
    @PostMapping("/long")
    public String longUrl(@RequestParam("url") String url){
        return longUrlService.longUrl(url);
    }

}
