package com.scdt.interview.javat.controller;

import com.scdt.interview.javat.service.UrlService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/scdt", produces = MediaType.APPLICATION_JSON_VALUE)
public class UrlConverterController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/toSUrl")
    @ApiImplicitParam()
    @ApiOperation(value = "长地址转短地址，null表示异常", notes = "长地址转短地址，null表示异常")
    public String toSUrl(@ApiParam(name = "url", value = "url", required = true)
                         @RequestBody String url) {

        return urlService.toSUrl(url);
    }

    @PostMapping("/toLUrl")
    @ApiImplicitParam()
    @ApiOperation(value = "短地址转长地址，null表示异常", notes = "短地址转长地址，null表示异常")
    public String toLUrl(@ApiParam(name = "url", value = "url", required = true)
                         @RequestBody String url) {
        return urlService.toLUrl(url);
    }
}
