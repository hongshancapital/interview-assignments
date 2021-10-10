package com.scdt.interview.url.controller;

import com.scdt.interview.url.service.UrlService;
import com.scdt.interview.url.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author: lijin
 * @date: 2021年10月09日
 */
@Api(tags = "URL服务接口")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/url", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UrlController {

    private final UrlService urlService;


    @ApiOperation("存储长链接并返回短链接")
    @PostMapping(value = "/long", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public R storeLongUrl(@ApiParam("长链接") @NotBlank @URL @RequestParam String longUrl){

        return R.ok(urlService.storeLongUrl(longUrl));
    }

    @ApiOperation("获取长链接")
    @GetMapping("/long")
    public R getLongUrl(@ApiParam("短链接") @NotBlank @URL @RequestParam String shortUrl){
        String longUrl = urlService.getLongUrl(shortUrl);
        if(longUrl == null) return R.fail("长链接不存在");
        return R.ok(longUrl);
    }
}
