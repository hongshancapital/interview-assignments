package url.converter.facade.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import url.converter.common.CommonResult;
import url.converter.facade.vo.LongUrlGetReq;
import url.converter.facade.vo.ShortUrlAddReq;
import url.converter.service.UrlConverterService;

import javax.annotation.Resource;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Api(description = "长短链接接口")
@RestController
@RequestMapping(value = "/japi/uc")
public class UrlConverterController {

    @Resource
    private UrlConverterService urlConverterService;

    @PostMapping("/addShort")
    @ApiOperation("短链接存储接口")
    public CommonResult addShortUrl(@RequestBody ShortUrlAddReq req) {
        return CommonResult.success(urlConverterService.addShortUrl(req));
    }

    @GetMapping("/getLong")
    @ApiOperation("长链接获取接口")
    public CommonResult getLongUrl(LongUrlGetReq req) {
        return CommonResult.success(urlConverterService.getLongUrl(req));
    }
}
