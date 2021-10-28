package com.scdt.interview.assignments.web;

import com.scdt.interview.assignments.bean.vo.LongLinkParam;
import com.scdt.interview.assignments.bean.base.ResultEntity;
import com.scdt.interview.assignments.bean.vo.ShortLinkParam;
import com.scdt.interview.assignments.service.LinkService;
import com.scdt.interview.assignments.util.RsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 链接相关服务
 */
@RestController
@Api(produces = "application/json", tags = "API-链接服务", consumes = "application/json")
public class LinkController {

    @Autowired
    LinkService linkService;

    @PostMapping(value = "/generateShortUrl")
    @ApiOperation(value = "短域名存储接口", notes = "短域名存储接口")
    public ResultEntity generateShortUrl(@RequestBody @Valid LongLinkParam longLinkParam) {
        return RsUtil.success(linkService.generateShortUrl(longLinkParam));
    }

    @PostMapping(value = "/getLongtUrl")
    @ApiOperation(value = "短域名读取接口", notes = "短域名读取接口")
    public ResultEntity getLongtUrl(@RequestBody @Valid ShortLinkParam shortLinkParam) {
        return RsUtil.success(linkService.getLongUrl(shortLinkParam));
    }

}
