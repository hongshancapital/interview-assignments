package org.example.shorturl.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.common.ApiResult;
import org.example.shorturl.common.MyException;
import org.example.shorturl.constants.AppConstant;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 业务层的异常处理类,这里只是给出最通用的Exception的捕捉,后续根据业务需要,用不同的函数,处理不同类型的异常。
 *
 * @author bai
 * @date 2021-01-14
 */
@Slf4j
@RestControllerAdvice(basePackages = AppConstant.BASE_PACKAGES)
public class MyExceptionHandler {
    
    /**
     * 通用异常处理方法。
     *
     * @param ex      异常对象
     * @param request 请求Servlet
     * @return {@link ApiResult}<{@link Void}>
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<Void> argumentNotValidExceptionHandler(MethodArgumentNotValidException ex,
                                                            HttpServletRequest request) {
        log.error("Unhandled exception from URL [" + request.getRequestURI() + "]", ex);
        BindingResult exceptions = ex.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        List<ObjectError> errors = exceptions.getAllErrors();
        // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
        FieldError fieldError = (FieldError) errors.get(0);
        return ApiResult.failure(fieldError.getDefaultMessage());
    }
    
    /**
     * 业务异常处理
     *
     * @param ex      异常对象
     * @param request 请求Servlet
     * @return {@link ApiResult}<{@link Void}>
     */
    @ExceptionHandler(value = MyException.class)
    public ApiResult<Void> myExceptionHandle(MyException ex,
                                             HttpServletRequest request) {
        log.error("Unhandled exception from URL [" + request.getRequestURI() + "]", ex);
        return ApiResult.failure(ex.getMessage());
    }
}
