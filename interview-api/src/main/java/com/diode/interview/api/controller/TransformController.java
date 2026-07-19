package com.diode.interview.api.controller;

import com.diode.interview.api.converter.TransformConverter;
import com.diode.interview.api.models.enums.ResponseCodeEnum;
import com.diode.interview.api.models.request.TransformLongRequest;
import com.diode.interview.api.models.request.TransformShortRequest;
import com.diode.interview.api.models.response.BaseResponse;
import com.diode.interview.api.models.response.TransformResult;
import com.diode.interview.application.models.param.TransformLongParam;
import com.diode.interview.application.models.param.TransformShortParam;
import com.diode.interview.application.service.TransformService;
import com.diode.interview.domain.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Slf4j
@RestController
@RequestMapping("/transform")
@Api("短域名服务接口")
public class TransformController {
    @Resource
    private TransformService transformService;


    @ApiOperation(value = "根据长域名生成短域名")
    @PostMapping(value = "/shortURL/get")
    public BaseResponse<TransformResult> transformLong(@ApiParam("请求体") @RequestBody TransformLongRequest request) {
        BaseResponse<TransformResult> response;
        try{
            //参数校检
            TransformLongRequest.validate(request);
            //参数转换
            TransformLongParam transformLongParam = TransformConverter.convertTransformLongRequest(request);
            //执行业务逻辑
            String url = transformService.transformLongToShort(transformLongParam);
            //组装最终结果
            TransformResult transformResult = new TransformResult();
            transformResult.setUrl(url);
            response = BaseResponse.success(transformResult);
        }catch (IllegalArgumentException e){
            log.warn("参数异常, request:{}", request, e);
            response = BaseResponse.error(ResponseCodeEnum.ARGUMENT_ILLEGAL, e.getMessage());
        }catch (BizException e){
            log.error("业务异常, request:{}", request, e);
            response = BaseResponse.error(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }catch (Exception e){
            log.error("未知异常, request:{}", request, e);
            response = BaseResponse.error(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }
        log.debug("transformLong request:{} response:{}", request, response);
        return response;
    }

    @ApiOperation(value = "根据短域名生成长域名")
    @PostMapping(value = "/longURL/get")
    public BaseResponse<TransformResult> transformShort(@ApiParam("请求体") @RequestBody TransformShortRequest request) {
        BaseResponse<TransformResult> response;
        try{
            //参数校检
            TransformShortRequest.validate(request);
            //参数转换
            TransformShortParam transformShortParam = TransformConverter.convertTransformShortRequest(request);
            //执行业务逻辑
            String url = transformService.transformShortToLong(transformShortParam);
            //组装最终结果
            TransformResult transformResult = new TransformResult();
            transformResult.setUrl(url);
            response = BaseResponse.success(transformResult);
        }catch (IllegalArgumentException e){
            log.warn("参数异常, request:{}", request, e);
            response = BaseResponse.error(ResponseCodeEnum.ARGUMENT_ILLEGAL, e.getMessage());
        }catch (BizException e){
            log.error("业务异常, request:{}", request, e);
            response = BaseResponse.error(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }catch (Exception e){
            log.error("未知异常, request:{}", request, e);
            response = BaseResponse.error(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }
        log.debug("transformShort request:{} response:{}", request, response);
        return response;
    }
}
