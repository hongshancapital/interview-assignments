package com.hongshan.interfacejob.controller;

import com.hongshan.interfacejob.constant.CommonConstants;
import com.hongshan.interfacejob.exception.SystemException;
import com.hongshan.interfacejob.service.DomainNameService;
import com.hongshan.interfacejob.validator.DomainNameValidator;
import com.hongshan.interfacejob.vo.ShortUrlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = {"接口服务"}, description = "长短域名转换")
public class DomainNameController {

    @Autowired
    private DomainNameValidator domainNameValidator;

    @Autowired
    private DomainNameService domainNameService;

    @GetMapping("/queryShortByLong")
    @ApiOperation("根据长域名查询短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String", paramType ="query", required = true, defaultValue = "www.baidu.com")
    public ShortUrlVo getShortUrl(@RequestParam String longUrl) {

        String checkResult =  domainNameValidator.validateGetShortUrl(longUrl);
        ShortUrlVo vo = new ShortUrlVo();
        if(checkResult!=null && checkResult.length()>0){
            vo.setShortUrl(longUrl);
            vo.setMsg(checkResult);
            vo.setCode(CommonConstants.CODE_FAIL);
        }else {
            try {
                String shortUrl = domainNameService.getShortUrl(longUrl);
                vo.setLongUrl(longUrl);
                vo.setShortUrl(shortUrl);
                vo.setCode(CommonConstants.CODE_SUCCESS);
                vo.setMsg(CommonConstants.SUCCESS_MSG);
                return vo;
            } catch (SystemException ex) {
                vo.setLongUrl(longUrl);
                vo.setMsg(ex.getMessage());
                vo.setCode(ex.getCode());
            }
        }
        return vo;
    }

    @GetMapping("/queryLongByShort")
    @ApiOperation("根据短域名查询长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String", paramType ="query", required = true, defaultValue = "t.cn/bd")
    public ShortUrlVo getLongUrl(@RequestParam String shortUrl) {

        String checkResult = domainNameValidator.validateGetLongUrl(shortUrl);
        ShortUrlVo vo = new ShortUrlVo();
        if(checkResult!=null && checkResult.length()>0){
            vo.setShortUrl(shortUrl);
            vo.setMsg(checkResult);
            vo.setCode(CommonConstants.CODE_FAIL);
        }else{
            try {
                String longUrl = domainNameService.getLongUrl(shortUrl);
                vo.setLongUrl(longUrl);
                vo.setShortUrl(shortUrl);
                vo.setCode(CommonConstants.CODE_SUCCESS);
                vo.setMsg(CommonConstants.SUCCESS_MSG);
            } catch (SystemException ex) {
                vo.setShortUrl(shortUrl);
                vo.setMsg(ex.getMessage());
                vo.setCode(ex.getCode());
            }
        }
        return vo;
    }
}
