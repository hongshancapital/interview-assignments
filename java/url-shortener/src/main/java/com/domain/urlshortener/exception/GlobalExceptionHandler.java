package com.domain.urlshortener.exception;

import com.domain.urlshortener.enums.BizStatus;
import com.domain.urlshortener.model.FieldError;
import com.domain.urlshortener.model.ResponseModel;
import com.domain.urlshortener.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 20:51
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseModel exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        log.error("服务异常:", e);
        return ResponseModel.fail();
    }

    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public ResponseModel businessExceptionHandler(HttpServletRequest httpServletRequest, BizException e) {
        log.warn("业务异常: code - [" + e.getBizStatus().getCode() + "], msg - [" + e.getMessage() + "]");
        return ResponseModel.fail(e.getBizStatus(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel methodArgumentNotValidExceptionHandler(HttpServletRequest httpServletRequest, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errorList = new ArrayList();

        for (org.springframework.validation.FieldError fieldError : bindingResult.getFieldErrors()) {
            errorList.add(new FieldError(fieldError.getField(), MessageUtils.get(fieldError.getDefaultMessage())));
        }

        log.debug("参数校验异常：[" + Arrays.toString(errorList.toArray()) + "]");
        return new ResponseModel(BizStatus.BAD_REQUEST, errorList);
    }

}
