package com.scdt.domain.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.scdt.domain.common.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 目前是全局统一处理，可以细化不同exception 不同处理返回结果。
     * 例如：必填参数缺失，抛出特定异常的处理等
     * @param ex
     * @param req
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<R> handleOtherExceptions(final Exception ex, final WebRequest req) {
        R tResult = R.error(ExceptionUtil.stacktraceToString(ex));
        return new ResponseEntity(tResult, HttpStatus.OK);
    }
}
