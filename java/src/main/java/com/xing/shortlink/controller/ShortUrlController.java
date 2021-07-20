package com.xing.shortlink.controller;

import com.xing.shortlink.domain.http.request.CreateShortUrlRequest;
import com.xing.shortlink.domain.http.response.CreateShortUrlResponse;
import com.xing.shortlink.domain.http.request.QueryOriginalUrlRequest;
import com.xing.shortlink.domain.http.response.QueryOriginalUrlResponse;
import com.xing.shortlink.domain.http.Result;
import com.xing.shortlink.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接对外api
 *
 * @Author xingzhe
 * @Date 2021/7/17 21:42
 */
@RestController
@Api(value = "短链接服务API")
@RequestMapping("/")
@Slf4j
public class ShortUrlController {

    /**
     * 短链接核心服务
     */
    @Autowired
    UrlService urlService;

    /**
     * 生成短链接API
     *
     * @param request 短链接生成请求
     * @return 短链接生成应答
     */
    @ApiOperation("生成短链接")
    @PostMapping("createShortUrl")
    public Result<CreateShortUrlResponse> createShortUrl(@RequestBody @Validated CreateShortUrlRequest request) {
        return urlService.createShortUrl(request);
    }

    /**
     * 查询原始链接API
     *
     * @param request 查询原始链接请求
     * @return 查询原始链接应答
     */
    @ApiOperation("查询原链接")
    @PostMapping("queryOriginalUrl")
    public Result<QueryOriginalUrlResponse> queryOriginalUrl(@RequestBody @Validated QueryOriginalUrlRequest request) {
        return urlService.queryOriginalUrl(request);
    }
}
