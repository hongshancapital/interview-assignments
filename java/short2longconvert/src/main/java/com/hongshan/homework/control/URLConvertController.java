package com.hongshan.homework.control;

import com.hongshan.homework.service.URLConvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value="长短地址映射查询接口",tags="长短地址映射查询接口")
@RequestMapping("/api/url")
public class URLConvertController {

    @Autowired
    private URLConvertService urlConvertService;

    @GetMapping("/getShortURL")
    @ApiOperation(value="输入长地址，若短地址未使用，存储映射关系，返回短地址，否则返回错误提示ERROR1000-短地址重复，无法存储")
    public String getShortURL(@RequestParam(name="longURL") String longURL){
        String shortURL = null;
         shortURL = urlConvertService.getShortURL(longURL);
         log.info("shortURL:"+shortURL);
        return shortURL;
    }

    @GetMapping("/getLongURL")
    @ApiOperation(value="输入短地址，若长地址存在，返回长地址，否则返回ERROR2000-短地址不存在")
    public String getLongURL(@RequestParam(name="shortURL") String shortURL){
        String longURL = null;
        longURL = urlConvertService.getLongURL(shortURL);
        log.info("longURL:"+longURL);
        return longURL;
    }
}
