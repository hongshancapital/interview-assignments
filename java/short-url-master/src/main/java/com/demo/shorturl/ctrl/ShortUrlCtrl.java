package com.demo.shorturl.ctrl;

import com.demo.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Description: 生成短域名，获取长域名
 * @author : wangjianzhi
 * Create on 2021/9/17
 */
@RestController
@RequestMapping(value = "api/")
@Api(tags = "短域名服务", description = "通过长域名获取短域名，通过短域名获取长域名")
public class ShortUrlCtrl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlCtrl.class);

    @Autowired
    private ShortUrlService shortUrlServiceImpl;


    /**
     * 短域名冲突时最大重试次数
     */
    private static final int MAX_RETRY_NUM = 1;

    /**
     * 功能描述: 通过长域名获取短域名
     * @param longUrl 长域名
     * @return java.lang.Object
     * @author wangjianzhi
     * @throws
     * Create on 2021/9/17
     */
    @ApiOperation(value = "通过长域名获取短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String", required = true)
    @ApiResponse(code = 200, message = "获取成功")
    @PostMapping(value = "getShortUrl")
    public Object getShortUrl(@RequestParam String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            return "长域名不允许为空";
        }
        try {
            return shortUrlServiceImpl.getShortUrl(longUrl, MAX_RETRY_NUM);
        }
        catch (Exception e) {
            LOGGER.error("获取短域名异常", e);
            return "获取短域名失败";
        }
    }


    /**
     * 功能描述: 通过短域名获取长域名
     * @param shortUrl
     * @return java.lang.Object
     * @author wangjianzhi
     * @throws
     * Create on 2021/9/17
     */
    @ApiOperation(value = "通过短域名获取长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String", required = true)
    @ApiResponse(code = 200, message = "获取成功")
    @PostMapping(value = "getLongUrl")
    public Object getLongUrl(@RequestParam String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            return "短域名不允许为空";
        }
        try {
            return shortUrlServiceImpl.getLongUrl(shortUrl);
        }
        catch (Exception e) {
            LOGGER.error("获取长域名异常", e);
            return "获取长域名失败";
        }
    }
}