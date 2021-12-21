package com.hongshang.shorturlweb;

import com.hongshang.common.ComResult;
import com.hongshang.shorturlinterface.IShortUrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 短地址服务控制类
 *
 * @author kobe
 * @date 2021/12/19
 */
@Api(value = "短域名服务接口", description = "短域名服务接口操作API", tags = "ShortUrlControl", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ShortUrlControl {

    public static final String SUCCESSFUL_EXECUTION = "Successful execution";
    public static final String NOT_EXIT_LONG_URL = "longUrl  does not exist";

    /**
     * 服务实现业务bean
     */
    @Autowired
    private IShortUrlService shortUrlService;

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longUrl String 长域名
     * @return ComResult 短域名信息
     */
    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    @PostMapping(path = {"/getShortUrl"})
    public ComResult transformToShortUrl(@RequestBody  @ApiParam("长域名") String longUrl){
        ComResult comResult = null;
        try {
            if(StringUtils.isEmpty(StringUtils.trim(longUrl))){
                comResult = ComResult.FAIL("The longUrl parameter cannot be empty ");
                return comResult;
            }
            String shortUrl = shortUrlService.transformToShortUrl(longUrl);
            comResult = ComResult.SUCCESS(SUCCESSFUL_EXECUTION);
            comResult.setResult(shortUrl);
        }catch (Exception e){
            comResult = ComResult.FAIL(e.getMessage());
        }
        return comResult;
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     *
     * @param shortUrl String  短域名
     * @return ComResult 长域名信息
     */
    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    @GetMapping(path = {"/getLongUrl/{shortUrl}"})
    public ComResult getOriginalUrlByShortUrl(@PathVariable("shortUrl") @ApiParam("短域名") String shortUrl){
        ComResult comResult = null;
        try {
            if(StringUtils.isEmpty(StringUtils.trim(shortUrl))){
                comResult = ComResult.FAIL("The shortUrl parameter cannot be empty ");
                return comResult;
            }
            String longUrl = shortUrlService.getLongUrlByShortUrl(shortUrl);
            if(StringUtils.isEmpty(longUrl)){
                return  ComResult.FAIL(NOT_EXIT_LONG_URL);
            }
            comResult = ComResult.SUCCESS(SUCCESSFUL_EXECUTION);
            comResult.setResult(longUrl);
        }catch (Exception e){
            comResult = ComResult.FAIL(e.getMessage());
        }
        return comResult;
    }
}
