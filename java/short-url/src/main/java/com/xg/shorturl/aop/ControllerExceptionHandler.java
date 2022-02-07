package com.xg.shorturl.aop;

import com.xg.shorturl.common.BaseErrorCode;
import com.xg.shorturl.common.BaseResponse;
import com.xg.shorturl.common.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * controller异常处理：业务预期内异常info日志，其他非预期异常error日志
 * @author xionggen
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public BaseResponse errorHandler(BizException ex, HttpServletRequest request) {
        log.info("RequestURI:" + request.getRequestURI()
                + ",ErrorCode : " + ex.getCode()
                + ",ErrorMsg : " + ex.getMessage(), ex);
        return BaseResponse.newFailResponse()
                .errorCode(ex.getCode())
                .errorMsg(ex.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse errorHandler(RuntimeException ex, HttpServletRequest request) {
        log.error("RequestURI:" + request.getRequestURI()
                + ",RuntimeExceptionMsg:" + ex.getMessage(), ex);
        return BaseResponse.newFailResponse()
                .errorCode(BaseErrorCode.ERROR_SYSTEM_ERROR.getCode())
                .errorMsg("SYSTEM ERROR!")
                .build();
    }
}
