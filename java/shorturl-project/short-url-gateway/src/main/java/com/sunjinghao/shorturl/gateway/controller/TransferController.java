package com.sunjinghao.shorturl.gateway.controller;


import com.sunjinghao.shorturl.api.bo.DomainMappingInfoBo;
import com.sunjinghao.shorturl.api.vo.DomainMappingInfoVo;
import com.sunjinghao.shorturl.common.result.GlobalHttpResult;
import com.sunjinghao.shorturl.common.util.GlobalHttpResultUtil;
import com.sunjinghao.shorturl.gateway.domain.IUrlTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 长链转短链服务接口
 * swagger 增加版 localhost:8080/doc.html
 *
 * @author sunjinghao
 */
@Slf4j
@Api(tags = "短链服务")
@RestController
@RequestMapping("/trans")
public class TransferController {


    /**
     * 注入实现服务，分布式应用可注入 dubbo 或 spring cloud 服务
     */
    @Autowired
    private IUrlTransferService urlTransferService;

    /**
     * @param domainMappingInfoVo
     * @return
     */
    @ApiOperation(value = "短域名存储接口:接受长域名信息，返回短域名信息", response = DomainMappingInfoBo.class)
    @PostMapping(value = "/longToShort")
    @ResponseBody
//    @MonitorLog
    public GlobalHttpResult<DomainMappingInfoBo> getShortUrl( @RequestBody @Valid DomainMappingInfoVo domainMappingInfoVo) {
//        UrlValidator defaultValidator = new UrlValidator();
//        boolean valid = defaultValidator.isValid(domainMappingInfoVo.getUrl());
//        if (!valid) {
//            return GlobalHttpResultUtil.error("not a url");
//        }

        DomainMappingInfoBo domainMappingInfoBo = new DomainMappingInfoBo();
        domainMappingInfoBo.setUrl(domainMappingInfoVo.getUrl());

        domainMappingInfoBo = urlTransferService.longUrlMappingShortCode(domainMappingInfoBo);
        return GlobalHttpResultUtil.success(domainMappingInfoBo);
    }


    /**
     * @param code
     * @return
     */
    @ApiOperation(value = " 短域名读取接口：接受短域名信息，返回长域名信息", response = DomainMappingInfoBo.class)
    @GetMapping(value = "/getLongByShort/{code}")
    @ResponseBody
//    @MonitorLog
    public GlobalHttpResult<DomainMappingInfoBo> getLongUrl(@PathVariable String code) {
        if (StringUtils.isEmpty(code)) {
            return GlobalHttpResultUtil.error("code can not be empty or null");
        }

        DomainMappingInfoBo domainMappingInfoBo = new DomainMappingInfoBo();
        domainMappingInfoBo.setShortCode(code);

        domainMappingInfoBo = urlTransferService.getLongUrlByShortCode(domainMappingInfoBo);
        return GlobalHttpResultUtil.success(domainMappingInfoBo);
    }
}
