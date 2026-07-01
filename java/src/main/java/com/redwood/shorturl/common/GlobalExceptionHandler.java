package com.redwood.shorturl.common;

import com.redwood.shorturl.constant.ResponseEnum;
import com.redwood.shorturl.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局异常统一处理
 * @Author: Liu Jiangfeng
 * @Date: 2021-06-08 9:43
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @param e
     * @Author: Jack-ZG
     * @Description: 自定义异常处理
     * @Date: 2021/6/8 18:35
     * @return: com.midea.cloud.common.ResultBody
     **/
    @ExceptionHandler(BizException.class)
    public Result bizExceptionHandler(Exception e) {
        BizException exception = (BizException) e;
        return Result.error(ResponseEnum.BODY_NOT_MATCH.getCode(), exception.getMsg());
    }


}
