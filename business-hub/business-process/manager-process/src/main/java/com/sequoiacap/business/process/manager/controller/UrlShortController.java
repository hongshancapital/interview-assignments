package com.sequoiacap.business.process.manager.controller;

import com.sequoiacap.business.process.manager.controller.vo.CommonResVO;
import com.sequoiacap.business.process.manager.controller.vo.GetLongUrlResVO;
import com.sequoiacap.business.process.manager.controller.vo.SaveShortUrlReqVO;
import com.sequoiacap.business.process.manager.controller.vo.SaveShortUrlResVO;
import com.sequoiacap.business.process.manager.cst.ResponseCodeEnum;
import com.sequoiacap.business.process.manager.service.UrlShortService;
import com.sequoiacap.business.process.manager.util.MyValidationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *@ClassName: UrlController
 *@Description: 长域名转成短域名
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/urlShortController")
@Api(tags = "shortUrl")
@Slf4j
public class UrlShortController {

    @Autowired
    private UrlShortService urlShortService;

    /**
     * 返回指定地址对应的短链接
     * @param saveShortUrlReqVO
     * @return SaveShortUrlResVO
     */
    @ApiOperation(value = "长域名转短域名并保存", notes = "长域名转短域名并保存")
    @PostMapping(value = "/saveShortUrl")
    public CommonResVO saveShortUrl(@RequestBody SaveShortUrlReqVO saveShortUrlReqVO) {
        log.info("saveShortUrl : {}",saveShortUrlReqVO);
        CommonResVO commonResVO = new CommonResVO(ResponseCodeEnum.SUCC);
        SaveShortUrlResVO saveShortUrlResVO = new SaveShortUrlResVO();
        commonResVO.setData(saveShortUrlResVO);
        try {
            // 参数校验
            MyValidationUtils.validate(saveShortUrlReqVO);
            String shortUrl = urlShortService.generate(saveShortUrlReqVO.getLongUrl());
            saveShortUrlResVO.setShortUrl(shortUrl);

        }catch (Exception e){
            commonResVO.setCode(ResponseCodeEnum.FAIL.getCode());
            commonResVO.setMessage(e.getMessage());
        }

        return commonResVO;
    }

    /**
     * 返回指定地址对应的长链接
     * @param shortUrl
     * @return GetLongUrlResVO
     */
    @ApiOperation(value = "通过短域名获取长域名", notes = "通过短域名获取长域名")
    @GetMapping(value = "/getLongUrl/{shortUrl}")
    public CommonResVO getLongUrl(@PathVariable("shortUrl") @ApiParam(value = "短域名", required = true) String shortUrl) {
        log.info("shortUrl : {}",shortUrl);
        CommonResVO commonResVO = new CommonResVO(ResponseCodeEnum.SUCC);
        GetLongUrlResVO getLongUrlResVO = new GetLongUrlResVO();
        commonResVO.setData(getLongUrlResVO);
        try {
            String longUrl = urlShortService.get(shortUrl);
            getLongUrlResVO.setLongUrl(longUrl);
        }catch (Exception e){
            commonResVO.setCode(ResponseCodeEnum.FAIL.getCode());
            commonResVO.setMessage(e.getMessage());
        }
        return commonResVO;
    }
}
