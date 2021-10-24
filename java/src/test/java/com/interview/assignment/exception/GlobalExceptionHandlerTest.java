package com.interview.assignment.exception;

import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.response.APIResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Test
  public void testBusinessException() {
    BusinessException exception = new BusinessException(ResponseCode.SUCCESS);
    APIResponse<Object> result = globalExceptionHandler.businessException(exception);
    assertEquals(ResponseCode.SUCCESS.getCode(), result.getCode());
    assertEquals(ResponseCode.SUCCESS.getMessage(), result.getMessage());
  }

  @Test
  public void testException() {
    Exception exception = new Exception("test-message");
    APIResponse<Object> result = globalExceptionHandler.exception(exception);
    assertEquals(ResponseCode.SERVER_ERROR.getCode(), result.getCode());
    assertEquals("test-message", result.getMessage());
  }

  @Test
  public void testArgumentException() {
    FieldError fieldError = new FieldError("test", "test-field", "test-message");
    BindingResult bindingResult = Mockito.mock(BindingResult.class);
    when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
    APIResponse<Object> result = globalExceptionHandler.argumentException(exception);
    assertEquals(ResponseCode.ILLEGAL_PARAMS.getCode(), result.getCode());
    assertEquals("test-field: test-message;", result.getMessage());
  }

}
