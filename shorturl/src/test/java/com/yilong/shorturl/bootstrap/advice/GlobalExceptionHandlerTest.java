package com.yilong.shorturl.bootstrap.advice;


import com.yilong.shorturl.common.enums.ErrorEnum;
import com.yilong.shorturl.common.exception.BusinessException;
import com.yilong.shorturl.model.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @Test
    public void testGlobalExceptionHandler() {
        new GlobalExceptionHandler();
    }

    @Test
    public void testHandleGeneralException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Result result = globalExceptionHandler.handleGeneralException(new RuntimeException());
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getHttpStatus(), result.getStatus());
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode(), result.getError().getCode());
    }

    @Test
    public void testHandleBusinessException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Result result = globalExceptionHandler.handleBusinessException(new BusinessException("Error Short Code"));
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(Result.STATUS_ERROR, result.getStatus());
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode(), result.getError().getCode());
        Assertions.assertEquals("Error Short Code", result.getError().getMessage());
    }

    @Test
    public void testMethodArgumentNotValidException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(new ObjectError("shortUrl", "Invalid url")));
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        Result result = globalExceptionHandler.handleParameterVerificationException(methodArgumentNotValidException);
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), result.getStatus());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), result.getError().getCode());
        Assertions.assertEquals("Invalid url", result.getError().getMessage());
    }

    @Test
    public void testMethodArgumentTypeMismatchException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = mock(MethodArgumentTypeMismatchException.class);
        when(methodArgumentTypeMismatchException.getName()).thenReturn("url");

        Result result = globalExceptionHandler.handleParameterVerificationException(methodArgumentTypeMismatchException);
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), result.getStatus());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), result.getError().getCode());
        Assertions.assertEquals("Invalid url", result.getError().getMessage());
    }

    @Test
    public void testValidationException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ValidationException validationException = new ValidationException("Invalid url");

        Result result = globalExceptionHandler.handleParameterVerificationException(validationException);
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), result.getStatus());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), result.getError().getCode());
        Assertions.assertEquals("bad request params", result.getError().getMessage());
    }
}
