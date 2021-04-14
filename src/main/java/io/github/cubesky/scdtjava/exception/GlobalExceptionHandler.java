package io.github.cubesky.scdtjava.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局错误捕获器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 输入错误时返回 BadRequest 400
     * @param exception 自定义错误
     * @return 返回 404
     */
    @ExceptionHandler(value = RequestInvalidException.class)
    public ResponseEntity<?> requestInvalidException(RequestInvalidException exception) {
        return ResponseEntity.badRequest().build();
    }
}
