package com.sequoia.interview.controller;

import com.sequoia.interview.common.Result;
import com.sequoia.interview.common.ResultCodeEnum;
import com.sequoia.interview.common.ResultFactory;
import com.sequoia.interview.service.ShortDomainService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 17612735387@163.com
 * @date 2022/8/12 21:11
 **/
@RestController
@Slf4j
@RequestMapping("/short-domain")
public class ShortLinkController {

    @Autowired
    private ShortDomainService shortDomainService;

    @GetMapping("/get-short-link")
    @ApiOperation("store short link url from long link")
    public Result<String> getShortLink(String longLink){
        try {
            String shortLink = shortDomainService.generateShortLink(longLink);
            return ResultFactory.createSuccess(shortLink);
        }catch (Exception e){
            log.error("[getShortLink] error",e);
            return ResultFactory.createError(ResultCodeEnum.INNER_ERROR);
        }
    }

    @GetMapping("/get-long-link")
    @ApiOperation("get long link url from short link")
    public Result<String> getLongLink(String shortLink){
        try {
            String longLink = shortDomainService.findLongLink(shortLink);
            return ResultFactory.createSuccess(longLink);
        }catch (Exception e){
            log.error("[getLongLink] error",e);
            return ResultFactory.createError(ResultCodeEnum.INNER_ERROR);
        }
    }
}
