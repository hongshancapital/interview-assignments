package com.domain.controller;

import com.domain.bean.DomainRequestBean;
import com.domain.bean.ResultResponse;
import com.domain.bean.ShortDomainRequestBean;
import com.domain.exception.ExceptionEnums;
import com.domain.service.DomainService;
import com.domain.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：ji
 * @description：短域名服务接口
 */
@Api("短域名服务controller")
@RestController
@RequestMapping("/domain")
public class DomainController {
    private final static Logger logger = LoggerFactory.getLogger(DomainController.class);

    @Autowired
    private DomainService domainService;

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * 将url 地址转换成 短域名
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "短域名存储接口")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "成功"), @ApiResponse(code = 4000, message = "URL地址格式不正确!")})
    @RequestMapping(value = "/short",method = {RequestMethod.POST,RequestMethod.GET})
    public ResultResponse shortDomain(ShortDomainRequestBean bean){
        // 判断是否为合法的url
        if (!CommonUtil.isURL(bean.getUrl())){
            // URL格式不合法
            logger.error("shortDomain URL格式不合法");
            return new ResultResponse(ExceptionEnums.PARAM_URL_ERROR);
        }
        logger.info("shortDomain 开始转换URL={}",bean.getUrl());
        // 调服务转换短域名
        // 获取8位短域名识别符
        String sid = domainService.shortName(bean.getUrl());
        // 拼接请求地址
        String shortUrl = domainService.buildDomainInternetUrl(sid);
        return new ResultResponse(shortUrl);
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * 通过短域名获取 存储的url地址
     *
     * @param bean
     * @return
     */
    @ApiOperation("短域名读取接口")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "成功"), @ApiResponse(code = 4001, message = "参数不能为空!"),
            @ApiResponse(code = 4002, message = "非法链接!")})
    @RequestMapping(value = "/get",method = {RequestMethod.POST,RequestMethod.GET})
    public ResultResponse get(DomainRequestBean bean){
        // 校验shortUrl是否合法
        if (!StringUtils.hasText(bean.getShortUrl())){
            logger.error("get shortUrl为空");
            return new ResultResponse(ExceptionEnums.PARAM_NULL);
        }
        // 获取短域名映射
        String domainKey = domainService.getDomainKey(bean.getShortUrl());
        String url = domainService.getUrl(domainKey);
        return new ResultResponse(url);
    }

}
