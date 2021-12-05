package com.wwwang.assignment.shortenurl.controller;

import com.wwwang.assignment.shortenurl.entity.request.GetLongUrlReq;
import com.wwwang.assignment.shortenurl.entity.request.GetShortUrlReq;
import com.wwwang.assignment.shortenurl.entity.response.BizBaseResponse;
import com.wwwang.assignment.shortenurl.entity.response.GetLongUrlResp;
import com.wwwang.assignment.shortenurl.entity.response.GetShortUrlResp;
import com.wwwang.assignment.shortenurl.exception.BizException;
import com.wwwang.assignment.shortenurl.service.IShortenUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortenUrl")
@Api(tags = "获取长域名服务")
@Slf4j
@Validated
public class ShortenUrlController {

    @Autowired
    private IShortenUrlService shortenUrlService;

    @PostMapping("getShortUrl")
    @ApiOperation("获取APP信息")
    public BizBaseResponse<GetShortUrlResp> getShortUrl(@RequestBody @Validated @ApiParam GetShortUrlReq req){
        GetShortUrlResp resp;
        try {
            resp = new GetShortUrlResp(shortenUrlService.getShortUrl(req.getUrl()));
        } catch (Exception e){
            return processException(e);
        }
        BizBaseResponse response = BizBaseResponse.success(resp);
        return response;
    }

    @PostMapping("getLongUrl")
    @ApiOperation("获取APP信息")
    public BizBaseResponse<GetLongUrlResp> getLongUrl(@RequestBody @Validated @ApiParam GetLongUrlReq req){
        GetLongUrlResp resp;
        try {
            resp = new GetLongUrlResp(shortenUrlService.getLongUrl(req.getShortUrl()));
        } catch (Exception e) {
            return processException(e);
        }
        return BizBaseResponse.success(resp);
    }

    public static BizBaseResponse processException(Exception e){
        log.error("处理过程出现错误",e);
        if(e instanceof BizException){
            return BizBaseResponse.operationFailed(((BizException)e).getBizMsg());
        }
        return BizBaseResponse.operationFailed();
    }


}
