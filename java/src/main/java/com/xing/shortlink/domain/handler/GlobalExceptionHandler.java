package com.xing.shortlink.domain.handler;

import com.xing.shortlink.domain.exception.ExtensionException;
import com.xing.shortlink.domain.http.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.xing.shortlink.domain.entity.ErrorCodeEnum.PARAMETER_VALIDATION_ERROR;
import static com.xing.shortlink.domain.entity.ErrorCodeEnum.UNKNOWN_EXCEPTION;

/**
 * 通用异常捕获类
 *
 * @Author xingzhe
 * @Date 2021/7/17 22:59
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.xing"})
public class GlobalExceptionHandler {

    /**
     * 针对参数校验失败异常的处理
     *
     * @param exception 参数校验异常
     * @param request   http request
     * @return 异常处理结果
     */
    @ExceptionHandler(value = BindException.class)
    public Result<String> dataBindException(BindException exception,
                                            HttpServletRequest request) {

        Result<String> result = new Result<>();
        StringBuilder sb = new StringBuilder();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            sb.append("[").append(fieldName).append("]").append(error.getDefaultMessage()).append("; ");
        });
        result.setMessage(sb.toString());
        result.setCode(PARAMETER_VALIDATION_ERROR.getCode());
        log.error("参数异常!url:{},message:{}", request.getRequestURI(), sb.toString(), exception);
        return result;
    }

    /**
     * 针对全局异常的处理
     *
     * @param exception 全局异常
     * @param request   http request
     * @return 异常处理结果
     */
    @ExceptionHandler(value = Throwable.class)
    public Result<String> throwableHandler(Exception exception,
                                           HttpServletRequest request) {
        log.error("系统内部异常!url:{}", request.getRequestURI(), exception);
        return Result.fail(UNKNOWN_EXCEPTION.getCode(), UNKNOWN_EXCEPTION.getMsg());
    }

    /**
     * 业务异常处理
     *
     * @param exception 业务异常
     * @param request   http request
     * @return 异常返回
     */
    @ExceptionHandler(value = ExtensionException.class)
    public Result<String> extensionException(ExtensionException exception,
                                             HttpServletRequest request) {
        log.error("业务异常!request:{},code:{},message:{}", request.getRequestURI(), exception.getCode(), exception.getMessage());
        return Result.fail(exception.getCode(), exception.getMessage());
    }
}
