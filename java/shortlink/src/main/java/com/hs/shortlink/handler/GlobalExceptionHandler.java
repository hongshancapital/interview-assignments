package com.hs.shortlink.handler;

import com.hs.shortlink.domain.common.ResultVo;
import com.hs.shortlink.domain.constant.ResultStatusEnum;
import com.hs.shortlink.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理一般异常。
     */
    @ExceptionHandler(Exception.class)
    public ResultVo<Object> handle(Exception e) {
        log.error("CommonException ", e);
        return new ResultVo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "网络繁忙，请稍后重试!");
    }

    /**
     * 处理空指针异常。
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultVo<Object> handleNullPointException(NullPointerException e) {
        log.error("NullPointerException ", e);
        return new ResultVo<>(ResultStatusEnum.PARAM_INVALID, "传入参数错误", null);
    }

    /**
     * 参数绑定异常返回处理
     *
     * @param e MethodArgumentNotValidException
     * @return 结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<Object> handleConstraintViolationException(MethodArgumentNotValidException e) {
        String msg = buildErrMsg(e.getBindingResult());
        log.warn("MethodArgumentNotValidException 参数绑定异常: {}", msg, e);
        return new ResultVo<>(ResultStatusEnum.PARAM_INVALID, msg, null);
    }

    /**
     * 处理参数类型不匹配异常
     *
     * @param e MethodArgumentTypeMismatchException
     * @return 结果
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultVo<Object> handleConstraintViolationException(MethodArgumentTypeMismatchException e) {
        String msg = String.format("MethodArgumentTypeMismatchException 参数类型不匹配: %s", e.getMessage());
        log.warn(msg);
        return new ResultVo<>(ResultStatusEnum.PARAM_INVALID, msg, null);
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e ConstraintViolationException
     * @return 结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo<Object> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> errSet = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : errSet) {
            strBuilder.append(constraintViolation.getMessage());
        }
        String msg = strBuilder.toString();
        log.warn("ConstraintViolationException 接口数据验证异常: {}", msg);

        return new ResultVo<>(ResultStatusEnum.PARAM_INVALID, msg, null);
    }

    /**
     * 参数绑定异常返回处理
     *
     * @param e BindException
     * @return 结果
     */
    @ExceptionHandler(BindException.class)
    public ResultVo<Object> handleBindException(BindException e) {
        String msg = buildErrMsg(e.getBindingResult());
        log.warn("BindException msg:{}", msg, e);
        return new ResultVo<>(ResultStatusEnum.PARAM_INVALID, msg, null);
    }

    /**
     * 业务异常
     *
     * @param e BusinessException
     * @return 结果
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo<Object> handleBusinessException(BusinessException e) {
        log.error("BusinessException code:{} msg:{}", e.getCode(), e.getMessage());
        return new ResultVo<>(e.getCode(), e.getMessage(), null);
    }

    /**
     * NUMBER_FORMAT_ERROR
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResultVo<Object> handleNumberFormatException(NumberFormatException e) {
        log.warn("NumberFormatException msg:{}", e.getMessage());
        return new ResultVo<>(ResultStatusEnum.NUMBER_FORMAT_ERROR, e.getMessage(), null);
    }

    private String buildErrMsg(BindingResult br) {
        List<ObjectError> errList = br.getAllErrors();
        StringBuilder strBuilder = new StringBuilder();

        for (ObjectError error : errList) {
            if (strBuilder.length() > 0) {
                strBuilder.append(" | ");
            }
            if (error instanceof FieldError) {
                strBuilder.append("【").append(((FieldError) error).getField()).append("】");
            }
            strBuilder.append(error.getDefaultMessage());
        }

        if (strBuilder.length() > 160) {
            return "请求参数格式错误";
        }
        return strBuilder.toString();
    }
}
