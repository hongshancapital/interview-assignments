package org.example.sgenerate.configuration;

import org.example.sgenerate.model.ApiResult;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一Web异常处理器
 *
 * @author yadu
 */
@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultWebExceptionAdvice {


    /**
     * 统一异常处理
     *
     * @param e        异常
     * @param request  http请求
     * @param response http响应
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResult> exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(ApiResult.failed(e.getMessage()));
        return responseEntity;
    }


}
