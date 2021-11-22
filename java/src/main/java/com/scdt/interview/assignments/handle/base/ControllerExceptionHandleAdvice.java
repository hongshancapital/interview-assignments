package com.scdt.interview.assignments.handle.base;

import com.scdt.interview.assignments.bean.base.ResultEntity;
import com.scdt.interview.assignments.util.RsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandleAdvice {

    @ExceptionHandler
    public ResultEntity handler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        log.error("RuntimeException：", e);
        return RsUtil.error();
    }
}