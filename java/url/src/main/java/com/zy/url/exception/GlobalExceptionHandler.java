package com.zy.url.exception;

import com.zy.url.common.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 参数异常返回
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(
            {MethodArgumentNotValidException.class,
                    MethodArgumentTypeMismatchException.class,
                    HttpMessageNotReadableException.class, IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ResultVo<Void> argValidErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("参数异常,uri:" + req.getRequestURI(), e);
        return new ResultVo<>(ErrorEnum.DATA_VALID.getCode(), e.getMessage());
    }

    /**
     * 业务异常返回
     *
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public ResultVo<Void> GenericErrorHandler(HttpServletRequest req, BusinessException e) {
        log.error("业务异常,uri:" + req.getRequestURI(), e);
        return new ResultVo<>(((BusinessException) e).getErrorCode(), e.getMessage());
    }

    /**
     * 文件大小超出异常
     *
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(code = HttpStatus.PAYLOAD_TOO_LARGE)
    public ResultVo<Void> MaxFileErrorHandler(HttpServletRequest req, MaxUploadSizeExceededException e) {
        log.error("文件大小超出异常,uri:" + req.getRequestURI(), e);
        return new ResultVo<>(ErrorEnum.MAX_UPLOAD_SIZE.getCode(), e.getMessage());
    }

    /**
     * 所有未知异常返回
     *
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVo<Void> defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error("系统异常,uri:" + req.getRequestURI(), e);
        return new ResultVo<>(ErrorEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
