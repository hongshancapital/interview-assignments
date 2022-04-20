package com.link.common;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.link.enums.ResultInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @auth 十三先生
 * @date 2022-04-19
 * @desc
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 限流异常
     */
    @ExceptionHandler(FlowException.class)
    public HttpResult flowExceptionHandler(FlowException ex) {
        log.error("当前访问被限流");
        return HttpResult.createErrResult(ResultInfoEnum.QPS_LIMIT);
    }
}
