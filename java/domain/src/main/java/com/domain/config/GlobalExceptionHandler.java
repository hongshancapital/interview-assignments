package com.domain.config;

import com.domain.bean.ResultResponse;
import com.domain.exception.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jib
 * @desciption 统一异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final  static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获处理 Exception 异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultResponse globalException(Exception e){
        logger.error("globalException 未知异常",e);
        ResultResponse result = ResultResponse.fail();
        return result;
    }

    /**
     * 捕获处理 Exception 异常
     * @param e
     * @return
     */
    @ExceptionHandler(DomainException.class)
    public ResultResponse domainException(DomainException e){
        // logger打印日志中要打印堆栈信息，exception放参数最后一个即可
        logger.error("domainException code={},message={}",e.getCode(),e.getOriginMessage(),e);
        return new ResultResponse(e.getCode(),e.getMessage());
    }

}
