package com.scdt.china.shorturl.controller;

import com.scdt.china.shorturl.common.GlobalErrorCode;
import com.scdt.china.shorturl.common.ResultVo;
import com.scdt.china.shorturl.pojo.Url;
import com.scdt.china.shorturl.pojo.request.LongUrlRequest;
import com.scdt.china.shorturl.pojo.request.ShortUrlRequest;
import com.scdt.china.shorturl.pojo.response.LongUrlResponse;
import com.scdt.china.shorturl.pojo.response.ShortUrlResponse;
import com.scdt.china.shorturl.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhouchao
 * @Date: 2021/11/26 13:13
 * @Description:
 */
@Slf4j
@Api
@RestController
@RequestMapping("api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    /**
     * 短连接转换长连接接口
     *
     * @param shortUrlRequest 短链接地址
     * @return url {@link Url} 长链接信息
     */
    @ApiOperation(value = "短链接读取长链接接口", response = Url.class, httpMethod = "GET")
    @PostMapping("shortUrl")
    public ResultVo<ShortUrlResponse> getLongUrl(@RequestBody @Validated @ApiParam(value = "短链接的短码", name = "shortCode", required = true, example = "12345678") ShortUrlRequest shortUrlRequest) {
        return urlService.getLongUrl(shortUrlRequest);
    }

    /**
     * 短域名存储接口
     *
     * @param longUrlRequest 长连接地址
     */
    @ApiOperation(value = "短链接存储接口", response = String.class, httpMethod = "POST")
    @PostMapping("longUrl")
    public ResultVo<LongUrlResponse> saveShortUrl(@RequestBody @Validated @ApiParam(value = "长连接地址", name = "longUrl", required = true, example = "12345678") LongUrlRequest longUrlRequest) {
        return urlService.saveShortUrl(longUrlRequest);
    }

}
