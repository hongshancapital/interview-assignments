package com.yuanyang.hongshan.controller;

import com.yuanyang.hongshan.entity.RequestDTO;
import com.yuanyang.hongshan.entity.Result;
import com.yuanyang.hongshan.entity.ResultCode;
import com.yuanyang.hongshan.service.UrlService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "url转换接口", tags = {"url"})
@RequestMapping(value = "/url", produces = "application/json;")
public class UrlTransferController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/generateShortURL")
    public Result<String> generateShortURL(@RequestBody RequestDTO requestDTO) {
        if(null == requestDTO || StringUtils.isBlank(requestDTO.getLongUrl())){
            return Result.newErrorResult(ResultCode.ILLEGAL_PARAM);
        }
        Result<String> res = urlService.generateShortURL(requestDTO);
        return res;
    }

    @PostMapping("/getLongUrl")
    public Result<String> getLongUrl(@RequestBody RequestDTO requestDTO) {
        if(null == requestDTO || StringUtils.isBlank(requestDTO.getShortUrl()) ||
                requestDTO.getShortUrl().length() >8){
            return Result.newErrorResult(ResultCode.ILLEGAL_PARAM);
        }
        Result<String> res = urlService.getLongUrlByShortUrl(requestDTO);
        return res;
    }

}
