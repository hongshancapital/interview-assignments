package com.hongshan.link.service.controller;

import com.hongshan.link.service.entity.Result;
import com.hongshan.link.service.service.LinkConvertService;
import com.hongshan.link.service.valid.LinkControllerValidate;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author heshineng
 * created by 2022/8/8
 * link 长短链接转换的接口
 */
@RestController
@Api(tags = "链接转换模块")
public class LinkController {

    private static Logger log = LoggerFactory.getLogger(LinkController.class);

    @Resource
    private LinkConvertService linkConvertService;

    @PostMapping("/get/short/link")
    @ApiOperation(value = "根据长链接转换对应的短链接", response = Result.class)
    @ApiImplicitParam(name = "link", value = "需要转换的长链接地址", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 500, message = "失败")
    })
    public Result<String> getShortLink(String link) {
        try {
            if (LinkControllerValidate.checkUrl(link)) {
                return Result.success(linkConvertService.getShortLink(link));
            }
        } catch (Exception e) {
            log.error("getShortLink error url={}", link, e);
        }
        return Result.fail();
    }

    @PostMapping("/get/long/link")
    @ApiOperation(value = "根据短链接转换对应的长链接", response = Result.class)
    @ApiImplicitParam(name = "link", value = "需要转换的短链接地址", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 500, message = "失败")
    })
    public Result<String> getLongLink(String link) {
        try {
            if (LinkControllerValidate.checkShortUrl(link)) {
                return Result.success(linkConvertService.getLongLink(link));
            }
        } catch (Exception e) {
            log.error("getLongLink error url={}", link, e);
        }
        return Result.fail();
    }
}
