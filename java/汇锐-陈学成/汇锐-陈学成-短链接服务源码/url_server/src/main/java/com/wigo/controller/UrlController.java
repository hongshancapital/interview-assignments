package com.wigo.controller;

import com.wigo.core.CustomException;
import com.wigo.core.Result;
import com.wigo.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wigo.chen
 * @date 2021/7/27 9:18 下午
 * Introduction: 实现长链接转短链接服务
 */
@RestController
@CrossOrigin //解决跨域请求，可以在拦截器中设置，也可以在nginx等中间件中设置
@Slf4j
@Api(value = "测试接口", tags = "用户管理相关的接口")
public class UrlController {
    @Autowired
    UrlService urlService;

    /**
     * 长链接转短链接接口服务
     *
     * @param longUrl 长链接
     * @return com.wigo.core.Result 包含短链接或是错误信息
     * @author wigo.chen
     * @date 2021/7/27 9:19 下午
     **/
    @ApiOperation(value = "长链接转短链接", notes = "长链接转短链接")
    @PostMapping(value = "/getShortUrl")
    public Result getShortUrl(@ApiParam(required = true, value = "长链接") String longUrl) {
        try {
            log.info("--------------------");
            return Result.success(urlService.getShortUrl(longUrl));
        } catch (Exception e) {
            return Result.fail("生成短链接逻辑异常");
        }
    }

    /**
     * 短链接还原长链接服务
     *
     * @param shortUrl 短链接
     * @return com.wigo.core.Result 包含长链接或是错误信息
     * @author wigo.chen
     * @date 2021/7/27 9:20 下午
     **/
    @ApiOperation(value = "短链接转长链接", notes = "短链接转长链接")
    @PostMapping(value = "/getLongUrl")
    public Result getLongUrl(@ApiParam(required = true, value = "短链接") String shortUrl) {
        try {
            return Result.success(urlService.getLongUrl(shortUrl));
        } catch (CustomException e) {
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            return Result.fail("获取长链接逻辑异常");
        }
    }
}
