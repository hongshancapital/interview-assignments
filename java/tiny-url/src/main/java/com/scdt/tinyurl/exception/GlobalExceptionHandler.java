package com.scdt.tinyurl.exception;

import com.scdt.tinyurl.common.ErrorCode;
import com.scdt.tinyurl.common.ResponseResult;
import com.scdt.tinyurl.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult handleException(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return ResultUtil.error(globalException.getCode(), globalException.getMessage());
        } else if(e instanceof MethodArgumentNotValidException) {
            //当请求参数校验不通过时返回具体不通过的字段和原因
            List<ObjectError> errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            Map<String,Object> errorMap = new HashMap<>();
            errors.stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            });
            return ResultUtil.error(ErrorCode.ILLEGAL_REQUEST_PARAM.getCode(), ErrorCode.ILLEGAL_REQUEST_PARAM.getMsg(),errorMap);
        } else {
            log.error(e.getMessage());
            return ResultUtil.error(ErrorCode.SERVICE_UNKNOWN_ERROR.getCode(), ErrorCode.SERVICE_UNKNOWN_ERROR.getMsg());
        }
    }
}
