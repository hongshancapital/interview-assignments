package com.snail.shorturlservice.exception;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.snail.shorturlservice.common.response.ResultBean;
import com.snail.shorturlservice.common.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataInsertException.class)
    public ResultBean handleDataInsertException(Exception e) {
        LOGGER.error("数据入库异常", e);
        return ResultBean.fail(ResultCode.DATA_INSERT_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMessageList = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessageList.add(fieldError.getDefaultMessage());
        }
        return ResultBean.fail(ResultCode.PARAM_ERROR, StrUtil.join(",", errorMessageList));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultBean handleNoHandlerFoundException(Exception e) {
        LOGGER.error("未找到资源异常", e);
        return ResultBean.fail(ResultCode.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultBean handleDuplicateKeyException(DuplicateKeyException e) {
        LOGGER.error("重复主键异常", e);
        return ResultBean.fail(ResultCode.DUPLICATE_KEY);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResultBean handleJsonParseException(JsonParseException e) {
        LOGGER.error("json解析异常", e);
        return ResultBean.fail(ResultCode.PARAM_FORMAT_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResultBean handleException(Exception e) {
        LOGGER.error("全局异常", e);
        return ResultBean.fail(ResultCode.UNKNOWN_ERROR);
    }

}
