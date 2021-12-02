package com.zhangliang.suril.exception;

import cn.hutool.http.HttpException;
import com.zhangliang.suril.configuration.CodeMessageConfiguration;
import com.zhangliang.suril.controller.view.BaseResult;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;


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
    public static final String CURRENT_ENV_TEST = "test";

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * Exception 如果是测试环境，返货具体的错误消息 如果是开发环境，直接抛出 如果是生产环境，注意封装错误
     */
    @ExceptionHandler({Exception.class})
    public BaseResult<String> processException(Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        BaseResult<String> result = new BaseResult<>();
        log.error("请求发生错误", exception);
        if (env.equals(CURRENT_ENV_DEV)) {
            throw exception;
        } else if (env.equals(CURRENT_ENV_TEST)) {
            result.setMsg(exception.getMessage());
        } else {
            result.setMsg(CodeMessageConfiguration.getMessage(50000));
        }

        result.setCode(50000);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }
}
