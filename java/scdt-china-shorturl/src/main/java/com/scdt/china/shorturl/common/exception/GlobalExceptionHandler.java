package com.scdt.china.shorturl.common.exception;


import com.scdt.china.shorturl.common.GlobalErrorCode;
import com.scdt.china.shorturl.common.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:全局异常处理 <br/>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVo defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.info("GlobalExceptionHandler.handleException:{}", request.getRequestURI());
        String msg = "系统繁忙：" + ex.getMessage();
        String code = GlobalErrorCode.SYSTEM_ERROR.getCode();
        if (ex instanceof BizException) {
            handleStackTraceElement(ex);
            msg = ex.getMessage();
        } else if (ex instanceof MethodArgumentNotValidException) {
            StringBuffer sb = new StringBuffer();
            handleStackTraceElement(ex);
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            List<String> allError = e.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            msg = String.join(",",allError);
            code = GlobalErrorCode.PARAMETER_ERROR.getCode();
        }
        return ResultVo.failure(code, msg, null);
    }

    private void handleStackTraceElement(Exception ex) {
        StackTraceElement[] ste = ex.getStackTrace();
        if (ste != null) {
            StackTraceElement cause = ste[0];
            log.info("Exception Location [ClassName: " + cause.getClassName() + ", MethodName: "
                    + cause.getMethodName() + ", LineNumber: " + cause.getLineNumber() + "]");
        }
    }
}
