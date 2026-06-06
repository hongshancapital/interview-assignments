package com.duoji.shortlink.controller;

import com.duoji.shortlink.common.Result;
import com.duoji.shortlink.common.ResultBuilder;
import com.duoji.shortlink.domain.ShortLinkHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 13:14:00
 */
@RestController
@Slf4j
@Api("短链接服务")
public class ShortLinkController {
    @Autowired
    private ShortLinkHandler shortLinkHandler;

    @GetMapping("/getShortLink")
    @ApiOperation("长链生成短链")
    public Result<String> getShortLink(@ApiParam("长连接地址") String longLink){
        try {
            String shortLink = shortLinkHandler.generateShortLink(longLink);
            return ResultBuilder.buildSuccess(shortLink);
        }catch (IllegalArgumentException e){
            log.error("[getShortLink] error",e);
            return ResultBuilder.buildFailed(e.getMessage());
        }catch (Exception e){
            log.error("[getShortLink] error",e);
            return ResultBuilder.buildFailed("服务异常");
        }
    }

    @GetMapping("/getLongLink")
    @ApiOperation("短链获取长链")
    public Result<String> getLongLink(@ApiParam("短连接地址") String shortLink){
        try {
            String longLink = shortLinkHandler.findLongLink(shortLink);
            return ResultBuilder.buildSuccess(longLink);
        }catch (IllegalArgumentException e){
            log.error("[getLongLink] error",e);
            return ResultBuilder.buildFailed(e.getMessage());
        }catch (Exception e){
            log.error("[getLongLink] error",e);
            return ResultBuilder.buildFailed("服务异常");
        }
    }
}
