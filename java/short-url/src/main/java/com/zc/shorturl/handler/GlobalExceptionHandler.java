package com.zc.shorturl.handler;

import com.zc.shorturl.dto.BaseResponse;
import com.zc.shorturl.dto.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;


/**
 * 全局异常处理器
 *
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<ResultCode> handlerMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        // 按需重新封装需要返回的错误信息
        List<String> invalidArguments = new ArrayList<>();
      for (FieldError error : e.getBindingResult().getFieldErrors()) {
          invalidArguments.add(error.getDefaultMessage());
      }
      return BaseResponse.fail(ResultCode.PARAMETER_ERROR, String.join(",", invalidArguments));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse<ResultCode> handlerNoHandlerFoundException(Exception e) {
        log.error("未找到资源异常", e);
        return BaseResponse.fail(ResultCode.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public BaseResponse<ResultCode> handlerException(Exception e) {
        log.error("未知异常", e);
        return BaseResponse.fail(ResultCode.UNKNOWN_EXCEPTION);
    }
}
