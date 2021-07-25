package com.ccb.domain.ctrl;

import com.ccb.domain.service.ShortDomainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: nieyy
 * @Date: 2021/7/24 14:10
 * @Version 1.0
 * @Description: 根据长域名生成短域名
 */

@RestController
public class ShortDomainCtrl {

    @Autowired
    private ShortDomainService shortDomainService;

    @ApiOperation(value="获取短域名", notes="获取短域名")
    @GetMapping("/getShortDomain")
    public String getShortDomainName(HttpServletRequest request,String longDomainName){
        //String domain = request.getServerName();
        return shortDomainService.getShortDomainName(longDomainName);
    }

    @ApiOperation(value="获取长域名", notes="获取长域名")
    @GetMapping("/getLongDomain")
    public String getLongDomainName(HttpServletRequest request,String shortDomainName){
        //String domain = request.getServerName();
        return shortDomainService.getLongDomainName(shortDomainName);
    }


}
