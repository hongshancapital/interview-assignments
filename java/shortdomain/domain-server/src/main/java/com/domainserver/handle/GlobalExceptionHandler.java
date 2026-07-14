package com.domainserver.handle;

import com.domaincore.constants.ErrorInfoEnum;
import com.domaincore.exceptions.BusinessException;
import com.domaincore.vo.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 * @author Administrator
 * @Date 2021/9/21
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param exp
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultBody BusinessExceptionHandler(HttpServletRequest req, BusinessException exp) {
        LOGGER.error("发生业务异常！原因是:{}", exp.getMessage());
        return ResultBody.error(exp.getCode(), exp.getMessage());
    }

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param exp
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException exp) {
        LOGGER.error("发生空指针异常！原因是:{}", exp.getMessage());
        return ResultBody.error(ErrorInfoEnum.BODY_NOT_MATCH);
    }


    /**
     * 处理其他异常
     *
     * @param req
     * @param exp
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, Exception exp) {
        LOGGER.error("未知异常！原因是:{}", exp.getMessage());
        return ResultBody.error(ErrorInfoEnum.INTERNAL_SERVER_ERROR);
    }
}
