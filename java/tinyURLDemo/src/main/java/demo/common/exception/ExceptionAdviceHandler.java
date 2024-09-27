package demo.common.exception;

import demo.common.result.Result;
import demo.common.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionAdviceHandler {
    private static final String error_msg = "系统异常，请联系管理员！";

    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();

        return ResultUtil.serverError(error_msg);
    }
}
