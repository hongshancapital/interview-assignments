package com.weige.shorturl.controller;

import com.weige.shorturl.Service.DomainService;
import com.weige.shorturl.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "长短域名转换")
public class DomainController {
    private static String SERVER_URL = "http://127.0.0.1:8080/interview";

    @Autowired
    private DomainService domainService;

    @GetMapping("saveLongUrl")
    @ApiOperation(value = "保存一个长域名",notes="http://127.0.0.1:8080/interview/getUserInfo" )
    public Result saveLongUrl(HttpServletRequest request){
        String longUrl = "/interview/getUserInfo";
        String shortUrl = domainService.write(longUrl);
        return Result.success("长链接保存完成，复制短连接可以直接访问",SERVER_URL+shortUrl);
    }

    @GetMapping("getUserInfo")
    @ApiOperation(value = "根据短域名跳转到这个地址,并获取信息")
    public Result getUerInfo(HttpServletRequest request){
        return Result.success(null,"王志维--java高级|资深--13476286187--微信同号");
    }


}
