package com.assignment.exception;

import com.assignment.model.ResponseEnum;
import com.assignment.model.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

import static com.assignment.model.ResponseEnum.WRONG_REQUEST_PARAMETER;

/**
 * 全局异常处理
 * @author mrdiyewu@gmail.com
 * @date 2021/10/11 15:33
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数校验异常
     */
    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class, BindException.class})
    public ResponseVO validateExceptionHandler(Exception ex) {
        String message = null;
        if (ex instanceof MethodArgumentNotValidException) {
            // 默认拿到第一个错误提示
            message = ((MethodArgumentNotValidException) ex).getBindingResult()
                    .getAllErrors().get(0).getDefaultMessage();
        } else if (ex instanceof ValidationException) {
            message = ex.getMessage();
        } else if (ex instanceof BindException) {
            message = ((BindException) ex).getAllErrors().get(0).getDefaultMessage();
        }
        log.error("参数校验异常", ex);
        return ResponseVO.error(WRONG_REQUEST_PARAMETER, message);
    }

    /**
     * 其他未定义的 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseVO undefinedExceptionHandler(RuntimeException ex) {
        log.error("其他未定义异常", ex);
        return ResponseVO.error(2001,ex.getMessage());
    }

    /**
     * 其他未定义异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseVO undefinedExceptionHandler(Exception ex) {
        log.error("其他未定义异常", ex);
        return ResponseVO.error(ResponseEnum.FAILED);
    }

}
