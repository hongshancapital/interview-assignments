package com.domain.urlshortener.exception;

import com.domain.urlshortener.enums.BizStatus;
import com.domain.urlshortener.model.ResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 3:35
 */
public class GlobalExceptionHandlerTest {

    @Test
    public void test_globalExceptionHandler() {
        new GlobalExceptionHandler();
    }

    @Test
    public void test_exceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseModel responseModel = globalExceptionHandler.exceptionHandler(null, new IllegalArgumentException());
        Assertions.assertEquals(BizStatus.FAILED.getCode(), responseModel.getCode());
    }

    @Test
    public void test_businessExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseModel responseModel = globalExceptionHandler.businessExceptionHandler(null, new BizException("A"));
        Assertions.assertEquals(BizStatus.FAILED.getCode(), responseModel.getCode());
        Assertions.assertEquals("A", responseModel.getMessage());
    }

    @Test
    public void test_methodArgumentNotValidExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);

        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(new FieldError("station", "name", "should not be empty")));
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseModel responseModel = globalExceptionHandler.methodArgumentNotValidExceptionHandler(null, methodArgumentNotValidException);
        Assertions.assertEquals(BizStatus.BAD_REQUEST.getCode(), responseModel.getCode());
    }

}
