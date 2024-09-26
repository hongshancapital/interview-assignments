package com.wang.api;

import com.wang.service.LinkToolService;
import com.wang.util.CommResult;
import com.wang.util.LRUMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc: 短连接controller
 * author: wang
 * date: 2021-11-04
 * **/

@RestController
@Api("短链接生成controller")
public class LinkToolController {
    @Autowired
    private LinkToolService linkToolService;

     public  final  static LRUMap<String, String> map = new LRUMap<>(10000);

    @ApiOperation(value = "Link0101 生成短链接 状态：已完成")
    @GetMapping(value = "/v1.0/create/shortUrl", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommResult createShortUrl(@ApiParam(value = "长链接url", required = true) @RequestParam("longUrl") String longUrl,
                                            @ApiParam(value = "短链接长度") @RequestParam(value = "length", defaultValue = "8", required = false) Integer length) {
        if(!longUrl.startsWith("http")||!longUrl.startsWith("https")){
            return CommResult.error(0,"链接不合法",null);
        }
        String shortUrl = linkToolService.getShortUrl(length);
        map.put(shortUrl,longUrl);

        return CommResult.ok(shortUrl);
    }

    @ApiOperation(value = "Link0102 获取长链接 状态：已完成")
    @GetMapping(value = "/v1.0/get/longUrl", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommResult getLongUrl(@ApiParam(value = "短链接url", required = true) @RequestParam("shortUrl") String shortUrl) {
        if(!shortUrl.startsWith("http")||!shortUrl.startsWith("https")){
            return CommResult.error(0,"链接不合法",null);
        }
        String longUrl = map.get(shortUrl);
        if(StringUtils.isNotBlank(longUrl)){
            throw  new IllegalArgumentException("链接不存在");
        }
        return CommResult.ok(longUrl);
    }


}
