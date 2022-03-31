package com.domain.api.controller.domain;

import com.domain.api.request.domain.DomainReadRequest;
import com.domain.api.request.domain.DomainWriteRequest;
import com.domain.api.response.BaseResponse;
import com.domain.api.response.domian.DomainReadResponse;
import com.domain.api.response.domian.DomainWriteResponse;
import com.domain.bo.DomainBO;
import com.domain.service.domain.DomainService;
import com.domain.utils.web.annotation.ApiPermission;
import com.domain.utils.web.annotation.ApiRateLimiter;
import com.domain.utils.web.annotation.ApiReSubmit;
import com.domain.utils.web.enums.HttpResponseCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "短域名API")
@RestController
@RequestMapping("/domain")
public class DomainController {


    @Autowired
    private DomainService domainService;

    @ApiOperation(value = "短域名存储", notes = "短域名存储",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = DomainWriteResponse.class)
    @PostMapping("/write")
    @ApiPermission  //权限验证
    @ApiReSubmit   //防重复提交
    @ApiRateLimiter  //限流
    public BaseResponse<DomainWriteResponse> write(@RequestBody(required = true) DomainWriteRequest request) {
        DomainWriteResponse data=new DomainWriteResponse();
        DomainBO domainBO=new DomainBO();
        domainBO.setLongUrl(request.getLongUrl());
        domainBO=domainService.createShortDomain(domainBO);
        if(!domainBO.getIsSuccess()){
            if(StringUtils.isNotBlank(domainBO.getMsg())) return BaseResponse.buildFail(domainBO.getMsg());
            else return BaseResponse.buildFail(HttpResponseCodeEnum.BUSY);
        }
        data.setShortUrl(domainBO.getShortUrl());
        return BaseResponse.buildSuccess(data);
    }

    @ApiOperation(value = "短域名读取", notes = "短域名读取",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = DomainReadResponse.class)
    @ApiPermission
    @PostMapping("/read")
    public  BaseResponse<DomainReadResponse>  read(@RequestBody(required = true) DomainReadRequest request) {
        DomainReadResponse data=new DomainReadResponse();
        DomainBO domainBO=new DomainBO();
        domainBO.setShortUrl(request.getShortUrl());
        domainBO=domainService.getLongDomain(domainBO);
        if(!domainBO.getIsSuccess()){
            if(StringUtils.isNotBlank(domainBO.getMsg())) return BaseResponse.buildFail(domainBO.getMsg());
            else return BaseResponse.buildFail(HttpResponseCodeEnum.NOTFUND);
        }
        data.setLongUrl(domainBO.getLongUrl());
        return BaseResponse.buildSuccess(data);
    }
}
