package com.ttts.urlshortener.base.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.ttts.urlshortener.base.model.BaseResultCodeEnums;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class BusinessExceptionTest {

  @ParameterizedTest
  @EnumSource(BaseResultCodeEnums.class)
  void constructor(BaseResultCodeEnums codeEnums) {
      Exception mockE = mock(Exception.class);

      BusinessException actual = new BusinessException(codeEnums, mockE);

      assertEquals(codeEnums.getCode(), actual.getCode());
      assertEquals(codeEnums.getMsg(), actual.getMsg());
      assertEquals(mockE, actual.getCause());
  }

  @ParameterizedTest
  @EnumSource(BaseResultCodeEnums.class)
    void of(BaseResultCodeEnums codeEnums) {
        BusinessException actual = BusinessException.of(codeEnums);

        assertEquals(codeEnums.getCode(), actual.getCode());
        assertEquals(codeEnums.getMsg(), actual.getMsg());
    }
}