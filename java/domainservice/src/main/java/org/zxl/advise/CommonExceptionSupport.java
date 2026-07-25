package org.zxl.advise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice(annotations = {RestController.class})
public class CommonExceptionSupport {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleCommonException(HttpServletRequest request, Exception ex) {
        log.error("产生异常, {} ,{}", request.getServletPath(), ex);
        CommonResponse errorResponse = new CommonResponse();
        errorResponse.setMessage("InternalServerError");
        return errorResponse;
    }


    @ExceptionHandler({CommonException.class})
    @ResponseBody
    public ResponseEntity handleCommonException(HttpServletRequest request, CommonException ex) {
        log.error("产生异常,handleCommonException error, path:{}, {}", request.getServletPath(), ex);
        return new ResponseEntity(ex, HttpStatus.valueOf(ex.getStatusCode()));
    }

}
