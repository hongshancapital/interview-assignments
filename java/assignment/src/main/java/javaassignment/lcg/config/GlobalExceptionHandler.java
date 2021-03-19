package javaassignment.lcg.config;


import javaassignment.lcg.controller.BaseController;
import javaassignment.lcg.controller.v1.UrlController;
import javaassignment.lcg.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author: 栾晨光
 * @Date: 2021-03-18 18:55
 * @Email 10136547@qq.com
 * @Description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private static final Logger log = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    public Result defaultErrorHandler(HttpServletRequest req, HttpServletResponse rep, Exception e) throws Exception {


        //可用自定义异常捕捉跳转异常,然后自定义跳转动作不一定是写写错误日志
        if(e instanceof IOException)
        {
            log.error("短地址跳转失败" ,e);
            rep.setStatus(500);
            return setResultError("短地址跳转失败.");
        }
        else
        {
            log.error("出现异常." ,e);
            rep.setStatus(500);
            return setResultError(e.getMessage());

        }
    }
}