package url.converter.facade.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import url.converter.common.CommonResult;
import url.converter.common.constant.ErrorCode;
import url.converter.exception.BadRequestParamException;
import url.converter.exception.UrlConverterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 */
@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理一般异常。
     */
    @ExceptionHandler(Exception.class)
    public CommonResult handle(Exception e) {
        log.error("全局异常处理：", e);
        return CommonResult.error(ErrorCode.UNKNOWN_SERVER_ERROR.code(), "服务器开小差啦~");
    }

    @ExceptionHandler(BadRequestParamException.class)
    public CommonResult handleBadRequestParamException(BadRequestParamException e) {
        return CommonResult.error(ErrorCode.BAD_REQUEST_PARAM.code(), e.getMessage());
    }

    @ExceptionHandler(UrlConverterException.class)
    public CommonResult handleUrlConverterException(UrlConverterException e) {
        return CommonResult.error(ErrorCode.URL_HANDLE_EXCEPTION.code(), e.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> errSet = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder("");
        for (ConstraintViolation constraintViolation : errSet) {
            strBuilder.append(constraintViolation.getMessage()).append(" | ");
        }

        String msg = strBuilder.toString();
        if (log.isDebugEnabled()) {
            log.debug("接口数据验证失败: {}", msg);
        }

        return CommonResult.error(ErrorCode.BAD_REQUEST_PARAM.code(), msg);

    }

    /**
     * 消息体解析异常返回处理
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public CommonResult handleJsonParseException(HttpMessageConversionException e) {
        String msg = "无法解析请求的消息体，请检查输入是否合法";
        if (log.isDebugEnabled()) {
            log.debug(msg + " : {}", e.getMessage());
        }
        return CommonResult.error(ErrorCode.BAD_REQUEST_PARAM.code(), msg);
    }


    /**
     * 参数绑定异常返回处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleConstraintViolationException(MethodArgumentNotValidException e) {
        String msg = buildErrMsg(e.getBindingResult());
        if (log.isDebugEnabled()) {
            log.debug("参数绑定异常: {}", msg);
        }

        return CommonResult.error(ErrorCode.BAD_REQUEST_PARAM.code(), msg);
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult handleConstraintViolationException(MethodArgumentTypeMismatchException e) {
        String msg = String.format("参数类型不匹配: %s", e.getMessage());
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
        return CommonResult.error(ErrorCode.BAD_REQUEST_PARAM.code(), ErrorCode.BAD_REQUEST_PARAM.errorMsg());
    }


    /**
     * 参数绑定异常返回处理
     */
    @ExceptionHandler(BindException.class)
    public CommonResult handleBindException(BindException e) {
        String msg = buildErrMsg(e.getBindingResult());
        if (log.isDebugEnabled()) {
            log.debug("参数绑定异常: {}", msg);
        }
        msg = "参数绑定异常: " + msg;
        return CommonResult.error(ErrorCode.BAD_REQUEST_PARAM.code(), msg);
    }

    /**
     * METHOD_NOT_ALLOWED异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CommonResult handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return CommonResult.error(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    /**
     * HttpMediaTypeException异常
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleHttpMediaTypeException(HttpMediaTypeException e) {
        return CommonResult.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * BeanCreationNotAllowedException SpringBoot在停止的时候会导致此异常发生 （原因可能是springboot正在停止时依然有请求打过来, 或者是收到kill指令时还有请求在队列里面）
     */
    @ExceptionHandler(BeanCreationNotAllowedException.class)
    public CommonResult handleBeanCreationNotAllowedException(BeanCreationNotAllowedException e) {
        log.error("BeanCreationNotAllowedException异常捕获", e);
        return new CommonResult<>(HttpStatus.SERVICE_UNAVAILABLE.value(),
                HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(), null);
    }

    @ExceptionHandler(InvalidPropertyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleInvalidPropertyException(InvalidPropertyException e) {
        log.warn("请求参数异常：{}", e.getMessage(), e);
        return CommonResult.error(HttpStatus.BAD_REQUEST.value(), "请求参数无法解析!");
    }


    private String buildErrMsg(BindingResult br) {
        List<ObjectError> errList = br.getAllErrors();
        StringBuilder strBuilder = new StringBuilder();

        for (ObjectError error : errList) {
            if (strBuilder.length() > 0) {
                strBuilder.append(" | ");
            }
            strBuilder.append(error.getDefaultMessage());

        }

        return strBuilder.toString();
    }

}
