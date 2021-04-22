package com.capital.domain.controller;


import com.capital.domain.service.IConversionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangts
 * @Classname ConversionApplication
 * @Description 域名长短转换
 * @Date 2021/4/19
 * @Version V1.0
 */

@RestController
@RequestMapping("/api/conversion")
public class ConversionController {

    @Autowired
    private IConversionService conversionService;

    @GetMapping("/lang-to-short")
    @ApiOperation("1 短域名存储")
    @ApiImplicitParam(name = "longDomain", value = "长域名", paramType = "query", dataType = "String", required = true)
    public String LangToShort(String longDomain) {
        return conversionService.langToShort(longDomain);
    }

    @GetMapping("/get-lang")
    @ApiOperation("2 短域名读取")
    @ApiImplicitParam(name = "shortDomain", value = "短域名", paramType = "query", dataType = "String", required = true)
    public String getLang(String shortDomain) {
        return conversionService.getLang(shortDomain);
    }
}
