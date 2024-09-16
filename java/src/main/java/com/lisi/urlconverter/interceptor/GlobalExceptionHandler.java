package com.lisi.urlconverter.interceptor;

import com.lisi.urlconverter.enumeration.UCErrorType;
import com.lisi.urlconverter.model.UCException;
import com.lisi.urlconverter.util.ResponseUtil;
import com.lisi.urlconverter.vo.UCResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 异常捕捉拦截器
 * @author: li si
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UCException.class)
    public UCResponse handleUCException(UCException e){
        return ResponseUtil.buildErrorResponse(e.getUcErrorType());
    }

    @ExceptionHandler(Exception.class)
    public UCResponse handleCommonException(Exception e){
        return ResponseUtil.buildErrorResponse(UCErrorType.COMMON_EXCEPTION);
    }
}
