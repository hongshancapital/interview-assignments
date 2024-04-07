package com.wangxiao.shortlink.resource;

import com.wangxiao.shortlink.applicaiton.ShortLinkService;
import com.wangxiao.shortlink.infrastructure.common.ErrorEnum;
import com.wangxiao.shortlink.infrastructure.common.Result;
import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("shortlink")
@Api("短链接编解码服务")
@Slf4j
public class ShortLinkResource {

    @Resource
    private ShortLinkService shortLinkService;

    @Resource
    private ShortLinkProperties shortLinkProperties;


    @PostMapping(value = "encode")
    @ApiOperation("长链接编码")
    public Result<String> encode(@ApiParam("长连接地址") @RequestParam String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return Result.fail(ErrorEnum.PARAMS_ILLEGAL);
        }
        log.info("长链接编码,req:{}", longLink);
        String shortLink = shortLinkService.encodeUrl(longLink);
        log.info("长链接编码,req:{},resp:{}", longLink, shortLink);
        return Result.success(shortLink);
    }

    @PostMapping(value = "decode")
    @ApiOperation("短链接解码")
    public Result<String> decode(@ApiParam("短连接地址") @RequestParam String shortLink) {
        if (StringUtils.isEmpty(shortLink) || !shortLink.startsWith(shortLinkProperties.getMachineId())) {
            return Result.fail(ErrorEnum.PARAMS_ILLEGAL);
        }
        log.info("短链接解码,req:{}", shortLink);
        String longLink = shortLinkService.decodeUrl(shortLink);
        log.info("短链接解码,req:{},resp:{}", shortLink, longLink);
        if (StringUtils.isEmpty(longLink)) {
            return Result.fail(ErrorEnum.LINK_NOT_EXISTS);
        }
        return Result.success(longLink);
    }
}
