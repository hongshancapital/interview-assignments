package com.example.shortlink.infrastructure.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, BizException exception) {
        String message = exception.getMessage();
        String code = exception.getCode();
        Result result = new Result(code, message);
        logger.error("Biz exception ", exception);
        return result;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity handleNoHandlerFound(final NoHandlerFoundException exception, final NativeWebRequest request) {
        String message = "not found";
        String code = "404";
        Result result = new Result(code, message);
        logger.warn("NoHandlerFoundException ", exception);
        return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity handleMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception, final NativeWebRequest request) {
        String message = "Request Method Not Supported";
        String code = "405";
        Result result = new Result(code, message);
        logger.warn("handleMethodNotSupportedException ", exception);
        return ResponseEntity.status(405).contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity methodDtoNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> {
            errorMsg.append(x.getDefaultMessage()).append(";");
        });
        Result result = new Result("400", errorMsg.toString());
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity validationErrorHandler(ConstraintViolationException ex) {
        List<String> errorInformation = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        Result result = new Result("400", ex.getLocalizedMessage());
        logger.warn("validationErrorHandler ", ex);
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(result);

    }

}
