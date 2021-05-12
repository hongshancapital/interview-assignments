package net.rungo.zz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.rungo.zz.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Api("测试域名服务")
@Controller
@RequestMapping("/domainService")
public class DomainController {

    @Autowired
    DomainService domainService;

    /**
     * 短域名存储接口
     * 接受长域名信息，返回短域名信息
     *
     * @param longDomain
     * @return
     */
    @ApiOperation(tags = {"短域名存储接口"}, value = "短域名存储")
    @RequestMapping(value = "/shortDomainSave", method = RequestMethod.POST)
    @ResponseBody
    public String shortDomainSave(String longDomain) throws ExecutionException, InterruptedException {

        return domainService.shortDomainSaveHandle(longDomain);
    }

    /**
     * 短域名读取接口
     * 接受短域名信息，返回长域名信息
     *
     * @param shortDomain
     * @return
     */
    @ApiOperation(tags = {"短域名读取接口"}, value = "短域名读取")
    @RequestMapping(value = "/shortDomainRead", method = RequestMethod.POST)
    @ResponseBody
    public String shortDomainRead(String shortDomain) {
        return domainService.shortDomainReadHandle(shortDomain);
    }
}
