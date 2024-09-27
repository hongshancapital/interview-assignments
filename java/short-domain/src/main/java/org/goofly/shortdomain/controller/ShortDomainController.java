package org.goofly.shortdomain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.goofly.shortdomain.service.ShortDomainService;
import org.goofly.shortdomain.utils.ResultBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : goofly
 * @Email: 709233178@qq.com
 */
@RestController
@Api("链接转换服务")
@Slf4j
public class ShortDomainController {

    @Autowired
    private ShortDomainService shortDomainService;

    @GetMapping("/generateShortDomian")
    @ApiOperation("生成短连接")
    public ResultBase<String> generateShortDomian(@ApiParam("长链接") String url) {
        try {
            String shortCode = shortDomainService.convertShortDomain(url);
            return ResultBase.successRsp(shortCode);
        } catch (IllegalArgumentException e) {
            log.error("get short link failed.param:{}", url, e);
            return ResultBase.errorRsp(e.getMessage());
        } catch (Exception e) {
            log.error("get short link failed.param:{}", url, e);
            return ResultBase.errorRsp("system error.");
        }
    }

    @GetMapping("/convertOriginalUrl")
    @ApiOperation("转换源链接")
    public ResultBase<String> convertOriginalUrl(@ApiParam("短码") String shortCode) {
        try {
            String originalUrl = shortDomainService.convertOriginalDomain(shortCode);
            return ResultBase.successRsp(originalUrl);
        }catch (IllegalArgumentException e) {
            log.error("get short link failed.param:{}", shortCode, e);
            return ResultBase.errorRsp(e.getMessage());
        } catch (Exception e) {
            log.error("get short link failed.param:{}", shortCode, e);
            return ResultBase.errorRsp("system error.");
        }
    }
}