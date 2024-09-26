package com.wenchao.jacoco.demo.exception;

import com.wenchao.jacoco.demo.utils.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author Wenchao Gong
 * @date 2021-12-15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 方法参数校验异常处理
     *
     * @param e 校验异常
     * @return 统一返回格式
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Ret<Void> handleMethodArgumentNotValidException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return Ret.error(e.getMessage());
    }
}
