package pers.zhufan.shorturl.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import pers.zhufan.shorturl.config.ShortUrlConfig;
import pers.zhufan.shorturl.domain.LongUrl;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import pers.zhufan.shorturl.dto.RespData;
import pers.zhufan.shorturl.service.ShortUrlService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 接口服务入口控制层
 */
@RestController
@Api(value = "短链接转换服务", tags = {"短链接转换服务"})
@RequestMapping(value = "/shorturl", produces = "application/json")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @Resource
    private ShortUrlConfig config;

    @ApiOperation(value = "生成短链")
    @RequestMapping(value = "/generalShortUrl", method = {RequestMethod.POST})
    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = RespData.class)})
    @ResponseBody
    public RespData<ShorterUrl> generalShortUrl(HttpServletRequest request, @RequestBody LongUrl longUrl) {

        ShorterUrl shorterUrl = null;

        try {
            shorterUrl = shortUrlService.generalShortUrl(config, longUrl.getLongUrl());
        }catch (Exception e){
            return new RespData<ShorterUrl>().fail("短链接转换异常！",null);
        }

        return new RespData<ShorterUrl>().success("短链接转换成功！",shorterUrl);
    }

    @ApiOperation(value = "将短链接转换为长链接")
    @RequestMapping(value = "/recoverLongUrl", method = {RequestMethod.POST})
    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS", response = RespData.class)})
    @ResponseBody
    public RespData<LongUrl> recoverLongUrl(HttpServletRequest request, @RequestBody ShorterUrl shorterUrl) {

        String longUrl = "";

        try {
            longUrl = shortUrlService.recoverLongUrl(shorterUrl.getShorter());
        }catch (Exception e){
            return new RespData<LongUrl>().fail("恢复长链接异常！",null);
        }

        return new RespData<LongUrl>().success("恢复长链接成功！",new LongUrl(longUrl));
    }

}
