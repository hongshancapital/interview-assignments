package com.example.controller;

import com.example.annotation.AccessLimit;
import com.example.util.ValidatorUtil;
import com.example.bean.result.ApiResult;
import com.example.service.ShortAddressService;
import com.example.bean.result.ResultRpc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述:
 * 对外提供rest请求接口服务
 *
 * @author eric
 * @create 2021-07-21 4:56 下午
 */
@Api("地址转换api")
@RestController
public class ShortAddressController {

    @Autowired
    ShortAddressService shortAddressService;

    /**
     * 用户登录
     * -- @AccessLimit 注解  在5秒内最多只有请求3次登录接口,超过则抛出异常
     * @param longAddress      长地址
     * @return ApiResult<?>
     */
    @AccessLimit(maxCount = 3, seconds = 5)
    @ApiOperation("长地址转换短地址")
    @RequestMapping(value = "/shorter/gen", method = RequestMethod.GET)
    public ApiResult genShorterByLongAddress(@RequestParam String longAddress) {
        //校验参数
        ValidatorUtil.checkValidateParam(longAddress);
        ResultRpc resultRpc = shortAddressService.genShortAddress(longAddress);
        if(resultRpc.isSuccess()) {
            return ApiResult.success(resultRpc.getData());
        } else {
            return ApiResult.error(resultRpc);
        }
    }
}
