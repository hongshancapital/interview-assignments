package com.mortimer.shortenurl.handler;

import com.mortimer.shortenurl.enums.ResponseStatusEnum;
import com.mortimer.shortenurl.model.ResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_exceptionHandler() {
        ResponseModel responseModel = globalExceptionHandler.exceptionHandler(null, new IllegalArgumentException());
        Assertions.assertEquals(ResponseStatusEnum.ERROR.getCode(), responseModel.getCode());
    }

    @Test
    public void test_methodArgumentNotValidExceptionHandler() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);

        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(new FieldError("station", "name", "msg.validation.url.invalid")));
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseModel responseModel = globalExceptionHandler.methodArgumentNotValidExceptionHandler(null, methodArgumentNotValidException);
        Assertions.assertEquals(ResponseStatusEnum.FAILED.getCode(), responseModel.getCode());
    }

    @Test
    public void test_constraintViolationExceptionHandler() {
        ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);

        when(constraintViolationException.getConstraintViolations()).thenReturn(new HashSet<>());
        ResponseModel responseModel = globalExceptionHandler.constraintViolationExceptionHandler(constraintViolationException);
        Assertions.assertEquals(ResponseStatusEnum.FAILED.getCode(), responseModel.getCode());
    }
}
