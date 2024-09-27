package com.hongshan.shorturl.config;

import com.hongshan.shorturl.model.exception.AbstractBizException;
import com.hongshan.shorturl.model.resps.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: huachengqiang
 * @date: 2022/1/15
 * @description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常处理
     *
     * @param exception
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AbstractBizException.class)
    public ResultVO<String> bizExceptionHandler(AbstractBizException exception, HttpServletRequest request) {
        logger.warn("biz exception happens, exception", exception);
        return ResultVO.error(exception.getCode(), exception.getMessage());
    }

    /**
     * 服务异常处理
     *
     * @param exception
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVO<String> unexpectExceptionHandler(Exception exception, HttpServletRequest request) {
        logger.warn("unexpect exception happens, exception", exception);
        return ResultVO.error(500, "服务异常，请联系xx处理");
    }
}
