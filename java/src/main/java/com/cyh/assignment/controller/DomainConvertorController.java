package com.cyh.assignment.controller;

import com.cyh.assignment.service.IDomainConvertorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "长短域名相互转换")
public class DomainConvertorController {

    @Autowired
    private IDomainConvertorService domainConvertorService;

    @RequestMapping(value = "saveShortDomain",method = RequestMethod.POST)
    @ApiOperation(value = "生成短域名",notes = "输入完整域名，返回短域名")
    public String saveShortDomain(@RequestParam(name = "fullDomain") String fullDomain) {
        try {
            return domainConvertorService.saveShortDomain(fullDomain);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "getFullDomain",method = RequestMethod.POST)
    @ApiOperation(value = "展示完整域名",notes = "输入短域名，返回完整域名")
    public String getFullDomain(@RequestParam(name = "shortDomain") String shortDomain) {
        try {
            return domainConvertorService.getFullDomain(shortDomain);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
