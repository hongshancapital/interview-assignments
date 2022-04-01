package com.zz.exception.controller;

import com.zz.controller.ShortUrlController;
import com.zz.exception.BusinessException;
import com.zz.exception.code.ShortUrlErrorCodeEnum;
import com.zz.param.Response;
import com.zz.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 短链接的集中异常处理类
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@Slf4j
@ResponseBody
@RestControllerAdvice(basePackageClasses = {ShortUrlController.class})
public class ShortUrlControllerAdvice {
    /**
     * 捕获业务异常
     *
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Response businessException(BusinessException businessException) {
        log.error("业务异常", businessException);
        return ResponseUtil.businessError(businessException);
    }

    /**
     * 参数错误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response paramError(MethodArgumentNotValidException exception) {
        try {
            log.error("参数错误", exception);
            return ResponseUtil.businessError(new BusinessException(ShortUrlErrorCodeEnum.SU_001, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), exception));
        }catch (Exception e){
            log.error("处理异常", e);
            return ResponseUtil.businessDefault();
        }
  }

    /**
     * 捕获其他异常数据
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response otherError(Exception exception) {
        log.error("系统错误", exception);
        return ResponseUtil.businessDefault();
    }
}
