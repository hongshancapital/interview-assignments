/**
 * this is a test project
 */

package com.example.interviewassgnments.handler;

import com.example.interviewassgnments.entitys.BaseResult;
import com.example.interviewassgnments.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理类
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:26
 * @Description: com.example.interviewassgnments.handler
 * @version: 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(value= BusinessException.class)
    public BaseResult businessException(BusinessException e, HttpServletRequest request) {
        //此处返回json数据
        log.error("msg:{},url:{}", e.getMessage(), request.getRequestURL());
        // 发送邮件通知技术人员.
        return BaseResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value= Exception.class)
    public BaseResult unknownException(Exception e, HttpServletRequest request) {
        //此处返回json数据
        log.error("msg:{},url:{}", e.getMessage(), request.getRequestURL());
        // 发送邮件通知技术人员.
        return BaseResult.error(-99, "系统出现错误, 请联系网站管理员!");
    }
}
