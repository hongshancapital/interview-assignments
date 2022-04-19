package com.mortimer.shortenurl.handler;

import com.mortimer.shortenurl.enums.ResponseStatusEnum;
import com.mortimer.shortenurl.model.ErrorInfo;
import com.mortimer.shortenurl.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseModel exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        log.error("服务异常:", e);
        return new ResponseModel(ResponseStatusEnum.ERROR, null);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel methodArgumentNotValidExceptionHandler(HttpServletRequest httpServletRequest,
                                                                MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ErrorInfo> errorList = new ArrayList();

        for (org.springframework.validation.FieldError fieldError : bindingResult.getFieldErrors()) {
            errorList.add(new ErrorInfo(fieldError.getField(), getI18nMsg(fieldError.getDefaultMessage())));
        }

        return new ResponseModel(ResponseStatusEnum.FAILED, errorList);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseModel constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return new ResponseModel(ResponseStatusEnum.FAILED, message);
    }

    private String getI18nMsg(String msgKey) {
        return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
    }
}
