package cn.sequoiacap.links.web.exception;

import cn.sequoiacap.links.web.vo.Result;
import cn.sequoiacap.links.web.vo.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Liushide
 * @date :2022/4/6 12:36
 * @description : web框架全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 json 请求体调用接口对象参数校验失败抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<List<String>> jsonParamsException(MethodArgumentNotValidException e) {
        log.error("触发 jsonParamsException ");
        // 一般会记录异常到系统中，这里只提示不做处理

        BindingResult bindingResult = e.getBindingResult();
        List<String> errorList = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = String.format("%s%s；", fieldError.getField(), fieldError.getDefaultMessage());
            errorList.add(msg);
        }

        return Result.failure(ResultCodeEnum.PARAMS_IS_INVALID, errorList);
    }

    /**
     *
     * @param e 未知异常捕获
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> unNoException(Exception e) {
        log.error("触发 unNoException ", e);
        // 一般会记录异常到系统中，这里只提示不做处理
        return Result.failure(ResultCodeEnum.INTERNAL_SERVER_ERROR, "请查找服务器日志排查！");
    }

}
