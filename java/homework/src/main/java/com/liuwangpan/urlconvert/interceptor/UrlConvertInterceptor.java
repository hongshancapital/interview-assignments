package com.liuwangpan.urlconvert.interceptor;

import com.liuwangpan.urlconvert.common.UrlConvertException;
import com.liuwangpan.urlconvert.model.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理器
 */
@ControllerAdvice
@Order(value = -1000)
@Slf4j
public class UrlConvertInterceptor {


    /**
     * 业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(UrlConvertException.class)
    @ResponseBody
    public BaseResponse handleException(UrlConvertException ex) {
        log.error("操作异常", ex);
        BaseResponse result = new BaseResponse();
        result.setMsg(ex.getMessage());
        result.setCode(ex.getCode());
        return result;
    }

}