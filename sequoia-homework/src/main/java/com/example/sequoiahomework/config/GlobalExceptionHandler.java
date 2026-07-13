package com.example.sequoiahomework.config;


import com.example.sequoiahomework.common.ex.MaximumMemoryException;
import com.example.sequoiahomework.common.response.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Irvin
 * @description 全局异常拦截
 * @date 2021/10/9 18:11
 */
@RestControllerAdvice(annotations = {RestController.class})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 默认统一异常处理方法
     *
     * @param e 注解用来配置需要拦截的异常类型, 也可以是自定义异常
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.Object>
     * @author Irvin
     * @date 2021/10/9
     */
    @ExceptionHandler(Exception.class)
    // 此处可以指定返回的状态码 和 返回 结果说明
    // @ResponseStatus(reason = "exception",value = HttpStatus.BAD_REQUEST)
    public DataResult<Object> runtimeExceptionHandler(Exception e) {
        // 打印异常信息到控制台
        e.printStackTrace();
        log.error("请求出现异常,异常信息为: ", e);
        // 使用公共的结果类封装返回结果, 这里我指定状态码为 500
        return new DataResult<>(0, 500, null, "程序异常");
    }

    /**
     * 参数校验异常的拦截
     *
     * @param ex 参数校验异常
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.Object>
     * @author Irvin
     * @date 2021/10/9
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
        log.info("参数校验异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return new DataResult<>(0, 400, null, "参数不合法" + fieldError.getDefaultMessage() + ";" + fieldError.getField());
    }

    /**
     * 参数绑定异常
     *
     * @param ex 绑定异常
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.Object>
     * @author Irvin
     * @date 2021/10/9
     */
    @ExceptionHandler(BindException.class)
    public DataResult<Object> handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
        log.info("参数校验异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return new DataResult<>(0, 400, null, "参数不合法" + fieldError.getDefaultMessage() + ";" + fieldError.getField());
    }


    /**
     * 内存溢出异常
     *
     * @param ex 内存溢出异常
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.Object>
     * @author Irvin
     * @date 2021/10/10
     */
    @ExceptionHandler(MaximumMemoryException.class)
    public DataResult<Object> maximumMemoryException(MaximumMemoryException ex) {
        log.info("可用内存已到达上限");
        return new DataResult<>(0, 400, null, "参数不合法" + ex.getMessage());
    }
}
