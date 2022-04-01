package com.zz.controller;

import com.zz.exception.BusinessException;
import com.zz.param.Response;
import com.zz.param.inparam.ShortUrlQueryParam;
import com.zz.param.inparam.ShortUrlStoreParam;
import com.zz.param.outparam.ShortUrlDTO;
import com.zz.ratelimit.RateLimiterAnno;
import com.zz.service.ShortUrlService;
import com.zz.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接的controller类，
 * 功能实现：1、包括从短链code映射到实际的url，
 * 2、从实际的url生成新的短链code
 * 说明：因为目前实现保存短链路的方式是通过内存缓存实现，
 * 所以使用了lru的算法，在容量不足够存放更多短链路映射的时候，清理旧的映射关系
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@Api(value = "短链接服务controller", tags = {"短链接服务接口，存储和获取"})
@RestController
@RequestMapping(path = "/short-url")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * 短链接存储接口：接受长域名信息，返回短域名信息
     *
     * @param shortUrlStoreParam
     * @return
     */
    @ApiOperation("短链接存储接口")
    @RateLimiterAnno(key = "store", QPS = 500, timeout = 20)
    @PostMapping(path = "/store")
    public Response<ShortUrlDTO> storeShortUrl(@RequestBody @Validated @ApiParam(name = "shortUrlStoreParam", value = "短链接存储参数", required = true) ShortUrlStoreParam shortUrlStoreParam) throws BusinessException {
        return ResponseUtil.buildSuccess(shortUrlService.storeShortUrl(shortUrlStoreParam));
    }

    /**
     * 短链接获取接口：接受短域名信息，返回长域名信息
     *
     * @param shortUrlQueryParam
     * @return
     */
    @ApiOperation("短链接获取接口")
    @RateLimiterAnno(key = "query", QPS = 5000, timeout = 20)
    @PostMapping(path = "/query")
    public Response<ShortUrlDTO> queryShortUrl(@RequestBody @Validated @ApiParam(name = "shortUrlQueryParam", value = "短链接获取参数", required = true) ShortUrlQueryParam shortUrlQueryParam) throws BusinessException {
        return ResponseUtil.buildSuccess(shortUrlService.queryShortUrl(shortUrlQueryParam));
    }
}
