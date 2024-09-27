package com.scdt.api;

import com.scdt.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UrlApi 长短URL转换API接口
 *
 * @Author: lenovo
 * @since: 2021-12-15
 */
@Api(value = "URL API")
@RestController("urlApi")
@RequestMapping("url")
public class UrlApi {

    @Autowired
    private UrlService urlService;

    @ApiOperation(value = "",notes="将长url转换成短url返回",nickname = "获取短url")
    @GetMapping("put")
    public String put(String longUrl){
      return urlService.put(longUrl);
    }

    @ApiOperation(value = "",notes="根据短url获取完整url",nickname = "获取完整url")
    @GetMapping("get")
    public String get(String shortUrl){
        return urlService.get(shortUrl);
    }
}
