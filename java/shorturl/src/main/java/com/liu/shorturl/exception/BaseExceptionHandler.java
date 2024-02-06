package com.liu.shorturl.exception;

import com.liu.shorturl.dto.RestResponse;
import com.liu.shorturl.enums.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description： 全局异常捕获处理
 * Author: liujiao
 * Date: Created in 2021/11/11 18:28
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    /**
     * 异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestResponse handlerException(Exception e) {
        /*if(e instanceof BusinessException) {
            //业务异常
            return RestResponse.failure(ResultCodeEnum.SERVER_ERROR);
        }*/
        // ....可以增加其他更多异常判断
        //系统异常
        return RestResponse.failure(ResultCodeEnum.SERVER_ERROR);
    }


}
