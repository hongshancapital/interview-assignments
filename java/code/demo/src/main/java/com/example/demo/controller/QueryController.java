package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "长短网址转换相关接口")
public class QueryController {
    @ResponseBody
    @GetMapping("/query/{id}")
    @ApiOperation(value = "读取或者存储接口", notes = "接口描述", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "参数1:[write->表示将长网址存储为短网址，并返回短网址|read->表示用短网址查询长网址，返回长网址]", required = true, paramType = "path"),
            @ApiImplicitParam(name = "url",value = "参数2:[若id是write则参数是长网址，若id是read则参数是短网址]", required = true, paramType = "query"),
    })
    public String query(@PathVariable(name = "id") String id, @RequestParam(name = "url") String url) {
        if (id.equals("read")) {
            //url是短链接
            String ori = RealtimeHot.queryShortFromURL(url);
            if (ori.length() == 0) {
                ori="no such short url";
            }
            //String current = String.format("shor url = {%s} <=> long url = {%s}", url, ori);
            String current = ori;
            return current;
        } else if (id.equals("write")) {
            //url是长链接
            String shorUrl = RealtimeHot.writeURL2Short(url);
            //String current = String.format("long url = {%s} <=> short = {%s}", url, shorUrl);
            String current = shorUrl;
            return current;
        } else {
            return "interference is wrong! read/write?url=****";
        }

    }

}
