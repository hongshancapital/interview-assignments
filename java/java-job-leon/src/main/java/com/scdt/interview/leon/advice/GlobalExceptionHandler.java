package com.scdt.interview.leon.advice;

import com.scdt.interview.leon.spec.MException;
import com.scdt.interview.leon.spec.MConstants;
import com.scdt.interview.leon.spec.MResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  全局Control层异常处理器
 *
 * @author leon
 * @since 2021/10/26
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /*
     * 处理ServiceException
     */
    @ExceptionHandler(value = MException.class)
    public Object serviceExceptionHandler(HttpServletResponse res, MException me) {
        log.info("业务逻辑异常", me);
        MResponse.buildHttpResponse(res, MResponse.failure(me));
        return null;
    }

    /*
     * 处理MethodArgumentNotValidException（校验异常）
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object methodArgumentNotValid(HttpServletResponse res, MethodArgumentNotValidException  nve)  {
        log.info("参数校验异常", nve);
        List<ObjectError> errors = nve.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));

        MResponse.buildHttpResponse(res, MResponse.buildResponse(MConstants.RTN_CODE_BADREQUEST, errorMsg.toString(), null));
        return null;
    }

    /*
     * 处理其他Exception
     */
	@ExceptionHandler(value = Exception.class)
    public Object commonExceptionHandler(HttpServletResponse res, Exception e) {
        log.info("未知错误", e);
        MResponse.buildHttpResponse(res, MResponse.failure("服务器出现未知错误，请稍后再试"));
        return null;
    }

}
