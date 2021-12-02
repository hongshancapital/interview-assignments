package com.zhangliang.suril.exception;

import com.zhangliang.suril.configuration.CodeMessageConfiguration;
import com.zhangliang.suril.controller.view.BaseResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理程序 简单起见这里只 handler 全局 exception，后面可以补上各种异常的 handler
 *
 * @author zhang
 * @date 2021/12/02
 */
@Order
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    public static final String CURRENT_ENV_DEV = "dev";

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * Exception 如果是测试环境，返货具体的错误消息 如果是生产环境，注意封装错误
     */
    @ExceptionHandler({Exception.class})
    public BaseResult<String> processException(Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        BaseResult<String> result = new BaseResult<>();
        result.setMsg(CodeMessageConfiguration.getMessage(50000));
        log.error("请求发生错误", exception);
        if (env.equals(CURRENT_ENV_DEV)) {
            result.setMsg(exception.getMessage());
        }

        result.setCode(50000);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }
}
