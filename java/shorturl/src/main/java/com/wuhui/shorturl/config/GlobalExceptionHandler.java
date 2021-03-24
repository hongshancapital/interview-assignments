package com.wuhui.shorturl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * 异常处理
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationExceptionHandle(Exception exception) {
        logger.error("Request error", exception);
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        Iterator<ConstraintViolation<?>> violationIterator = constraintViolations.iterator();
        if (violationIterator.hasNext()) {
            ConstraintViolation<?> constraintViolation = violationIterator.next();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraintViolation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exceptionHandle(Exception exception) {
        logger.error("Request error", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

}