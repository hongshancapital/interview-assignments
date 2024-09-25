package com.meihua.shorturl.controller;


import com.meihua.shorturl.cmdb.impl.IDGeneratorShortUrlHandler;
import com.meihua.shorturl.common.dto.BaseResponse;
import com.meihua.shorturl.common.enums.ResponseCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
@Controller
@RequestMapping("/api/id/")
@Api(tags = "发号器短链接生成相关接口")
public class IDShortUrlController {

    @Autowired
    private IDGeneratorShortUrlHandler idGeneratorShortUrlHandler;


    @ResponseBody
    @ApiOperation("根据长链返回短链的接口")
    @ApiImplicitParam(name = "url", value = "长链接地址")
    @RequestMapping(value = "/to_short_url",method = RequestMethod.POST)
    public BaseResponse<String> toShortUrl(@RequestParam(name = "url") String url){
        if (StringUtils.isEmpty(url)){
            return BaseResponse.error(ResponseCodeEnum.PARAMETER_IS_NULL);
        }
        String value = idGeneratorShortUrlHandler.put(url);
        return BaseResponse.success(value);
    }

    @ResponseBody
    @ApiOperation("根据短链接返回长链接")
    @RequestMapping(value = "/get_url", method = RequestMethod.GET)
    @ApiImplicitParam(name = "url", value = "短链接参数")
    public BaseResponse<String> getUrl(@RequestParam(name = "url") String url){
        if (StringUtils.isEmpty(url)){
            return BaseResponse.error(ResponseCodeEnum.PARAMETER_IS_NULL);
        }
        String value = idGeneratorShortUrlHandler.getValue(url);
        return StringUtils.isEmpty(value)?
                BaseResponse.error(ResponseCodeEnum.DATA_IS_NULL):BaseResponse.success(value);
    }




}
