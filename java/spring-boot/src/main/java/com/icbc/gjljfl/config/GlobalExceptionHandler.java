package com.icbc.gjljfl.config;

import com.icbc.gjljfl.common.ResponseEntity;
import com.icbc.gjljfl.common.enums.ErrorEnum;
import com.icbc.gjljfl.entity.exception.BaseException;
import com.icbc.gjljfl.entity.exception.ParamErrorException;
import com.icbc.gjljfl.util.LogUtils;
import com.icbc.gjljfl.util.ResponseUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLSyntaxErrorException;

/**
 * @Description: 全局异常处理
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

   private static String serviceName = GlobalExceptionHandler.class.getSimpleName();

   @ExceptionHandler(value = Exception.class)
   public ResponseEntity exceptionHandler(Exception e) {
      String position = "exceptionHandler";
      e.printStackTrace();
      StackTraceElement[] messages = e.getStackTrace();
      int length = messages.length;
      LogUtils.error(serviceName, "exceptionHandler", "occur global exception", e.toString());
      if (!ArrayUtils.isEmpty(messages) && length > 0) {
         for (int i = 0; i < length; i++) {
            LogUtils.error(serviceName, position, "exception Class name:", messages[i].getClassName());
            LogUtils.error(serviceName, position, "exception file name:", messages[i].getFileName());
            LogUtils.error(serviceName, position, "exception line number", messages[i].getLineNumber() + "");
            LogUtils.error(serviceName, position, "exception method name:", messages[i].getMethodName());
            LogUtils.error(serviceName, position, "exception to String:", messages[i].toString());
         }
      }
      return ResponseUtils.toFail(ErrorEnum.BUSINESS_FAIL);
   }

   /**
    * GET/POST请求方式错误
    *
    * @param e
    * @return
    */
   @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
   @ResponseBody
   public ResponseEntity httpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
      ResponseEntity response = ResponseUtils.toFail(ErrorEnum.HTTP_METHOD_FAIL);
      return response;
   }

   /**
    * 参数不可解析
    *
    * @param e
    * @return
    */
   @ExceptionHandler(HttpMessageNotReadableException.class)
   @ResponseBody
   public ResponseEntity httpMessageNotReadableException(HttpMessageNotReadableException e) {
      ResponseEntity response = ResponseUtils.toFail(ErrorEnum.PARAM_READABLE_ERROR);
      return response;
   }

   /**
    * 处理参数校验异常
    *
    * @param e
    * @return
    */
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseBody
   public ResponseEntity methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
      String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
      ResponseEntity response = ResponseUtils.toFail(ErrorEnum.PARAM_ERROR.getOutterCode(), errorMessage);
      return response;
   }

   /**
    * 处理参数校验异常
    *
    * @param e
    * @return
    */
   @ExceptionHandler(ParamErrorException.class)
   @ResponseBody
   public ResponseEntity paramErrorExceptionHandle(ParamErrorException e) {
      ResponseEntity response = getResponse(e);
      return response;
   }

   /**
    * SQL语句错误
    *
    * @param e
    * @return
    */
   @ExceptionHandler(SQLSyntaxErrorException.class)
   @ResponseBody
   public ResponseEntity sqlSyntaxErrorExceptionException(SQLSyntaxErrorException e) {
      e.printStackTrace();
      ResponseEntity response = ResponseUtils.toFail(ErrorEnum.SQL_ERROR);
      return response;
   }

   /**
    * 处理自定义异常
    *
    * @param e
    * @return
    */
   private ResponseEntity getResponse(BaseException e) {
      ResponseEntity response = ResponseUtils.toFail(e.getCode(), e.getMessage());
      return response;
   }
}
