package com.sequoia.shorturl.common.exception.handler;

import cn.hutool.core.util.StrUtil;
import com.sequoia.shorturl.common.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yanggj
 * @Description: 统一异常处理
 * @Date: 2022/1/9 16:05
 * @Version: 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiResult<?> handleException(Exception e) {
        return ApiResult.failure(StrUtil.isNotBlank(e.getMessage()) ? e.getMessage() : "服务器异常");
    }

}
