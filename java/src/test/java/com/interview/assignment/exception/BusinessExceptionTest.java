package com.interview.assignment.exception;

import com.interview.assignment.enums.ResponseCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessExceptionTest {
  @Test
  public void testNewBusinessException() {
    BusinessException exception = new BusinessException("test-code", "test-message");
    assertEquals("test-code", exception.getCode());
    assertEquals("test-message", exception.getMessage());
  }

  @Test
  public void testNewBusinessExceptionWithResponseCode() {
    BusinessException exception = new BusinessException(ResponseCode.SUCCESS);
    assertEquals(ResponseCode.SUCCESS.getCode(), exception.getCode());
    assertEquals(ResponseCode.SUCCESS.getMessage(), exception.getMessage());
  }
}
