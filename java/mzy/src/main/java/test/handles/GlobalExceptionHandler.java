package test.handles;


import static java.util.Collections.EMPTY_MAP;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import test.vo.ResultVo;

/**
 * @author miaozhiyan
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理一般异常。
     */
    @ExceptionHandler(Exception.class)
    public ResultVo handle(Exception e) {
        log.error("GlobalExceptionHandler 全局异常处理, ", e);
        return new ResultVo<>(500, e.getMessage(), EMPTY_MAP);
    }


    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> errSet = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : errSet) {
            strBuilder.append(constraintViolation.getMessage()).append(" ");
        }
        String msg = strBuilder.toString();
        log.info("GlobalExceptionHandler 接口数据验证异常: {} ", msg);
        return new ResultVo<>(500, msg, EMPTY_MAP);
    }

}
