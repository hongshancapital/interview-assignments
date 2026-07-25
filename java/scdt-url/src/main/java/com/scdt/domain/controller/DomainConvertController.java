package com.scdt.domain.controller;

import com.scdt.domain.common.R;
import com.scdt.domain.dto.LongUrlResult;
import com.scdt.domain.service.IDomainService;
import com.scdt.domain.dto.ShortUrlResult;
import com.scdt.domain.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Slf4j
@RestController
@RequestMapping("/domain/convert")
@Api(tags = {"域名服务"}, description = "域名转换服务")
public class DomainConvertController {

    @Resource
    IDomainService domainService;

    /**
     * 长链接转短链接
     * @param longUrl
     * @return
     */
    @PostMapping(value = "/getShortUrl")
    @ApiOperation(value = "长域名转短域名")
    @ApiImplicitParam(name = "longUrl",value = "长域名",required = true,dataType = "string",example = "https://cn.bing.com/")
    public R<ShortUrlResult> convertToShortUrl(@RequestParam(value = "longUrl") String longUrl){
        String result = domainService.getShortUrlByLongUrl(longUrl);
        LogUtil.debug(log,"短链接:{}",result);
        return R.success(new ShortUrlResult(result));
    }


    @PostMapping(value = "/getLongUrl")
    @ApiOperation(value = "短域名转长域名")
    @ApiImplicitParam(name = "shortUrl",value = "短域名",required = true,dataType = "string",example = "https://t.cn/RuPKzRW")
    public R<LongUrlResult> convertToLongUrl(@RequestParam(value = "shortUrl") String shortUrl){
        String result = domainService.getLongUrlByShortUrl(shortUrl);
        return R.success(new LongUrlResult(result));
    }
}
