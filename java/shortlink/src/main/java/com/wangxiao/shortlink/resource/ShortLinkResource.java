package com.wangxiao.shortlink.resource;

import com.wangxiao.shortlink.applicaiton.ShortLinkService;
import com.wangxiao.shortlink.infrastructure.common.ErrorEnum;
import com.wangxiao.shortlink.infrastructure.common.Result;
import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("shortlink")
@Api("短链接编解码服务")
public class ShortLinkResource {

    @Resource
    private ShortLinkService shortLinkService;

    @Resource
    private ShortLinkProperties shortLinkProperties;


    @PostMapping(value = "encode")
    @ApiOperation("长链接编码")
    public Result<String> encode(@ApiParam("长连接地址") String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            Result.fail(ErrorEnum.PARAMS_ILLEGAL);
        }
        return Result.success(shortLinkService.encodeUrl(longLink));
    }

    @PostMapping(value = "decode")
    @ApiOperation("短链接解码")
    public Result<String> decode(@ApiParam("短连接地址")String shortLink) {
        if (StringUtils.isEmpty(shortLink) || !shortLink.startsWith(shortLinkProperties.getMachineId())) {
            Result.fail(ErrorEnum.PARAMS_ILLEGAL);
        }
        String longLink = shortLinkService.decodeUrl(shortLink);
        if (StringUtils.isEmpty(longLink)) {
            Result.fail(ErrorEnum.LINK_NOT_EXSITS);
        }
        return Result.success(longLink);
    }
}
