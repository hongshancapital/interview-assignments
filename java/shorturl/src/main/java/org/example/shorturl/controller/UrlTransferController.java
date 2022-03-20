package org.example.shorturl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.common.ApiResult;
import org.example.shorturl.modal.LongUrlDto;
import org.example.shorturl.modal.ShortUrlDto;
import org.example.shorturl.service.UrlTransformService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 长短链接转换服务 控制曾
 *
 * @author bai
 * @date 2022/3/19 19:10
 */
@Slf4j
@RestController
@Api(tags = "长短链接转换服务")
@RequestMapping
public class UrlTransferController {
    
    /** url转换服务 */
    @Resource
    private UrlTransformService urlTransformService;
    
    /**
     * 根据长链接获取短链接
     *
     * @param urlDto url传输模型
     * @return {@link ApiResult}<{@link ShortUrlDto}>
     */
    @ApiOperation("长链接转短链接")
    @PostMapping(value = "longUrlToShortUrl")
    public ApiResult<ShortUrlDto> longUrlToShortUrl(@Validated @RequestBody LongUrlDto urlDto) {
        log.info("长链接转短链接:[{}]", urlDto);
        String shortUrl = urlTransformService.shortUrl(urlDto.getUrl());
        return ApiResult.success(new ShortUrlDto().setUrl(shortUrl));
    }
    
    /**
     * 根据短链接获取对应长链接
     *
     * @param urlDto url传输模型
     * @return {@link ApiResult}<{@link LongUrlDto}>
     */
    @ApiOperation("短链接转长链接")
    @PostMapping(value = "shortUrlToLongUrl")
    public ApiResult<LongUrlDto> shortUrlToLongUrl(@Validated @RequestBody ShortUrlDto urlDto) {
        log.info("短链接转长链接:[{}]", urlDto);
        String longUrl = urlTransformService.longUrl(urlDto.getUrl());
        return ApiResult.success(new LongUrlDto().setUrl(longUrl));
    }
}
