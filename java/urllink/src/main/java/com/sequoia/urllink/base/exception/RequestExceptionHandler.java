package com.sequoia.urllink.base.exception;

import com.sequoia.urllink.base.model.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

/**
 * 请求异常处理接口
 *
 * @author liuhai
 * @date 2022.4.15
 */
@ControllerAdvice(basePackages = {"com.sequoia.urllink.controller"} )
@Slf4j
public class RequestExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResultMessage<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ResultMessage<String>(ResultMessage.ResultCode.ERROR.getCode(), String.format("请求参数%s错误", ex.getParameterName()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResultMessage<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResultMessage<String>(ResultMessage.ResultCode.ERROR.getCode(), "请求参数长度超出限制");
    }
    // todo 通用异常跳转404页面?
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultMessage<String> handleException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new ResultMessage<String>(ResultMessage.ResultCode.ERROR.getCode(), "服务器错误");
    }
}
