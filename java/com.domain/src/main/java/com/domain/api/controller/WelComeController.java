package com.domain.api.controller;

import com.domain.bo.DomainBO;
import com.domain.service.domain.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "欢迎首页")
@Controller
public class WelComeController {

    @Autowired
    private DomainService domainService;

    @ApiOperation(value = "欢迎首页", notes = "欢迎首页")
    @RequestMapping("/{code}")
    @ResponseBody
    public String index(@PathVariable("code") String code) {
        String redirect_url=null;
        if(StringUtils.isNotBlank(code)){
            DomainBO domainBO=new DomainBO();
            domainBO.setAddressCode(code);
            domainBO=domainService.getLongDomain(domainBO);
            if(domainBO.getIsSuccess())redirect_url=domainBO.getLongUrl();
        }
        if(StringUtils.isNotBlank(redirect_url))  return "redirect:"+redirect_url;
        return "welcome domain";
    }
}
