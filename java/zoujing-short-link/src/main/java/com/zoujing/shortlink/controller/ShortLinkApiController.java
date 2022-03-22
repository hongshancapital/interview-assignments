package com.zoujing.shortlink.controller;

import com.zoujing.shortlink.exception.ShortLinkException;
import com.zoujing.shortlink.exception.ShortLinkResultEnum;
import com.zoujing.shortlink.model.ApiCallBack;
import com.zoujing.shortlink.model.ApiTemplate;
import com.zoujing.shortlink.model.CommonResult;
import com.zoujing.shortlink.model.ShortLinkRequest;
import com.zoujing.shortlink.service.LinkGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zoujing.shortlink.exception.ShortLinkResultEnum.*;

@RestController
@Slf4j
@Api("短链服务")
@RequestMapping("/shortLink")
public class ShortLinkApiController {

    @Autowired
    private LinkGeneratorService linkGeneratorService;

    @GetMapping("/getShortLink")
    @ApiOperation("长链接生成短链接")
    public CommonResult getShortLink(ShortLinkRequest request) {
        CommonResult result = new CommonResult(ShortLinkResultEnum.SYSTEM_ERROR, false);
        ApiTemplate.execute(result, new ApiCallBack() {
            @Override
            public void log() {
                log.info("receive getShortLink request.request = {}", request);
            }

            @Override
            public void check() {
                if (request.getSourceApp() == null || StringUtils.isEmpty(request.getLongLink())) {
                    throw new ShortLinkException(REQUEST_PARAM_ERROR);
                }
            }

            @Override
            public void process() {
                String shortLink = linkGeneratorService.getShortLink(request.getSourceApp(), request.getLongLink());
                if (StringUtils.isEmpty(shortLink)) {
                    throw new ShortLinkException(ID_GENERATOR_ERROR);
                }
                result.setData(shortLink);
            }
        });
        return result;
    }

    @GetMapping("/getLongLink")
    @ApiOperation("短链接获取对应长链接")
    public CommonResult getLongLink(ShortLinkRequest request) {
        CommonResult result = new CommonResult(ShortLinkResultEnum.SYSTEM_ERROR, false);
        ApiTemplate.execute(result, new ApiCallBack() {
            @Override
            public void log() {
                log.info("receive getLongLink request.request = {}", request);
            }

            @Override
            public void check() {
                if (request.getSourceApp() == null || StringUtils.isEmpty(request.getShortLink())) {
                    throw new ShortLinkException(REQUEST_PARAM_ERROR);
                }
            }

            @Override
            public void process() {
                String longLink = linkGeneratorService.getLongLink(request.getSourceApp(), request.getShortLink());
                if (StringUtils.isEmpty(longLink)) {
                    throw new ShortLinkException(SHORT_LINK_NOT_IN_CACHE);
                }
                result.setData(longLink);
            }
        });
        return result;
    }
}