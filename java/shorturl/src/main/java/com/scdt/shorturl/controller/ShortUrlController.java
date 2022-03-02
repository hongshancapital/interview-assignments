package com.scdt.shorturl.controller;

import cn.hutool.core.lang.Validator;
import com.scdt.shorturl.common.Result;
import com.scdt.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* @description url域名转换控制器
* @author Leo
* @date 2022/3/2 16:49
**/
@Api(tags = "短域名服务")
@Slf4j
@RestController
public class ShortUrlController {

        @Autowired
        private ShortUrlService shortUrlService;
        /**
        * @description: 长域名转短域名
        * @param url 长链接地址
        * @return: Result 段链接对象
        * @author: Leo
        * @date: 2022/3/2 16:50
        */
        @GetMapping(value = "longUrlToShortUrl")
        @ApiOperation("长域名转短域名")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "url", value = "长链接URL",required=true,example="https://jumps.sf-express.com/core/common/flash-message/?code=4coKS6ClvdgX&")
        })
        public Result<Object> longUrlToShortUrl(@Parameter(description = "长域名") @RequestParam String url) {

            if(Validator.isUrl(url)){
                String shortUrl = shortUrlService.shorterUrl(url);
                return Result.ok(shortUrl);
            }else{
                return Result.fail("无效的URL");
            }
        }

        /**
        * @description: 根据短域名获取对应长域名
        * @param shortUrl 短链接
        * @return:  Result 返回结果对象
        * @author: Leo
        * @date: 2022/3/2 16:50
        */
        @ApiOperation("根据短域名获取对应长域名")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "shortUrl", value = "短链接URL",required=true,example="http://t.cn/896950f4")
        })
        @GetMapping(value = "shortUrlToLongUrl")
        public Result<Object> shortUrlToLongUrl(@Parameter(description = "短域名") @RequestParam String shortUrl) {
            String url = shortUrlService.getOriginUrl(shortUrl);
            return Result.ok(url);
        }
}
