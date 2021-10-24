package com.interview.assignment.exception;

import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.response.APIResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(BusinessException.class)
  public APIResponse<Object> businessException(BusinessException exception) {
    log.error("business exception occurs, code: {}, message: {}", exception.getCode(), exception.getMessage());
    return APIResponse.fail(exception.getCode(), exception.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public APIResponse<Object> argumentException(MethodArgumentNotValidException exception) {
    BindingResult br = exception.getBindingResult();
    StringBuilder result = new StringBuilder();
    List<FieldError> feList = br.getFieldErrors();
    for (FieldError fe : feList) {
      String message = fe.getDefaultMessage();
      String field = fe.getField();
      result.append(field).append(": ").append(message).append("; ");
    }

    return APIResponse.fail(ResponseCode.ILLEGAL_PARAMS.getCode(), result.toString().trim());
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public APIResponse<Object> exception(Exception exception) {
    log.error("unknown exception", exception);
    return APIResponse.fail(ResponseCode.SERVER_ERROR.getCode(), exception.getMessage());
  }
}
