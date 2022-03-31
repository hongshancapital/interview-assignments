package com.domain.api.exception.handler;

import com.domain.api.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller控制层全局异常
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice(basePackages = {"com.domain.api"})
public class ControllerExceptionHandler {


    /**
     * 运行时异常
     * @param  runtimeException
     * @return ResponseEntity<BaseResponse>
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException runtimeException){
        return new ResponseEntity<BaseResponse>(BaseResponse.buildFail(runtimeException.getMessage()), HttpStatus.OK);
    }
}
