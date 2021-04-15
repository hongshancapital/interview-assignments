package com.demo.controller;

import com.demo.service.URLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gouyunfei on 2021/4/14.
 */

@RestController
@Api(tags = {"域名服务接口"}, value = "域名服务接口2")
public class URLController {
    @Autowired
    URLService urlService;

    @ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息", notes = "长URL")
    @GetMapping(value = "/set/{url}")
    public Object setUrl(@PathVariable(value = "url", required=true) String url ) throws Exception {

        String shortUrl = urlService.setUrl(url);
        if(shortUrl==null){
            Map modelMap = new HashMap<String, Object>();
            modelMap.put("error", "服务暂时停止");
            return modelMap;
        }
        Map modelMap = new HashMap<String, Object>();
        modelMap.put("url", shortUrl);
        return modelMap;
    }
    @ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息。", notes = "短URL")
    @RequestMapping(value = "/get/{url}", method = { RequestMethod.GET })
    public Object getUrl(@PathVariable(value = "url", required=true) String shortUrl) throws Exception {
        String longUrl = urlService.getUrl(shortUrl);
        if(longUrl==null){
            Map modelMap = new HashMap<String, Object>();
            modelMap.put("error", "暂无对应长连接");
            return modelMap;
        }
        Map modelMap = new HashMap<String, Object>();
        modelMap.put("url", longUrl);
        return modelMap;
    }


}
