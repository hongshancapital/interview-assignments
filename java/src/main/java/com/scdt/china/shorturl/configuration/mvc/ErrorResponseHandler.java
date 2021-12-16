package com.scdt.china.shorturl.configuration.mvc;

import com.scdt.china.shorturl.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

/**
 * 错误响应统一处理
 *
 * @author ng-life
 */
@ControllerAdvice
public class ErrorResponseHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorResponseHandler.class);

    /**
     * 客户端错误响应
     *
     * @param request   请求
     * @param exception 异常
     * @return 统一格式错误响应
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse clientError(NativeWebRequest request, BusinessException exception) {
        String eid = UUID.randomUUID().toString();
        HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUrl = Optional.ofNullable(httpServletRequest).map(HttpServletRequest::getRequestURI).orElse("unknown");

        LOG.error("客户端错误：错误ID = " + eid + ", URL=" + requestUrl, exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setEid(eid);
        errorResponse.setUrl(requestUrl);
        errorResponse.setErrorCode(exception.getErrorCode());
        errorResponse.setErrorMessage(exception.getMessage());
        return errorResponse;
    }

    /**
     * 服务端错误响应
     *
     * @param request   请求
     * @param exception 异常
     * @return 统一格式错误响应
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse serverError(NativeWebRequest request, Exception exception) {
        String eid = UUID.randomUUID().toString();
        HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUrl = Optional.ofNullable(httpServletRequest).map(HttpServletRequest::getRequestURI).orElse("unknown");

        LOG.error("服务端错误：错误ID = " + eid + ", URL=" + requestUrl, exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setEid(eid);
        errorResponse.setUrl(requestUrl);
        errorResponse.setErrorCode(9999);
        errorResponse.setErrorMessage("服务器内部错误");
        return errorResponse;
    }

}
