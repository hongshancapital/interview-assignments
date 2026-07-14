package com.wg.exception;

import com.wg.common.Result;
import com.wg.common.ResultCodeEnum;
import com.wg.common.ResultUtil;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> methodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResultUtil.error(ResultCodeEnum.PARAM_INVALID.getCode(), ResultCodeEnum.PARAM_INVALID.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public Result<Void> serviceException(ServiceException e){
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> exception(Exception e){
        return ResultUtil.error(ResultCodeEnum.SERVICE_ERROR.getCode(), ResultCodeEnum.SERVICE_ERROR.getMessage());
    }

}
