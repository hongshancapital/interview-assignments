package com.xwc.example.controller;

import com.xwc.example.commons.model.Result;
import com.xwc.example.controller.dto.DomainDto;
import com.xwc.example.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 类描述：处理短域名相关业务的控制器
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 17:41
 */
@RestController
@RequestMapping
@Api(tags = "短域名功能")
public class ShortDomainController {

    @Autowired
    @Qualifier("memoryDomainServiceImpl")
    private DomainService domainService;


    @PostMapping("/")
    @ApiOperation("长域名换短域名")
    public Result<DomainDto> LongDomainToShort(@RequestBody DomainDto domain) {
        if (!StringUtils.hasText(domain.getAddress())) {
            return Result.failed("address属性不能为空");
        }
        String shortDomain = domainService.getShortDomain(domain.getAddress());
        if (StringUtils.hasText(shortDomain)) {
            return Result.succeed(DomainDto.convert(shortDomain));
        }
        return Result.failed("短域名服务超过上限");
    }

    @GetMapping()
    @ApiOperation("短域名换长域名")
    public Result<DomainDto> shortDomainToLong(@ApiParam("短域名") @RequestParam String address) {
        if (!StringUtils.hasText(address)) {
            return Result.failed("address属性不能为空");
        }
        String lengthAddress = domainService.findLengthDomain(address);
        if (StringUtils.hasText(lengthAddress)) {
            return Result.succeed(DomainDto.convert(lengthAddress));
        }
        return Result.failed("长域名不存在");
    }
}
