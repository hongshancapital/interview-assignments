package com.ttts.urlshortener.base.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.ttts.urlshortener.base.exception.BusinessException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class BaseResultTest {

    @ParameterizedTest
    @ValueSource(strings = {"123", "456", ""})
    @NullSource
    void success(String data) {
        BaseResult actual = BaseResult.success(data);
        assertEquals(BaseResultCodeEnums.SUCCESS.getCode(), actual.getCode());
        assertEquals(BaseResultCodeEnums.SUCCESS.getMsg(), actual.getMessage());
        assertEquals(data, actual.getData());
    }

    @ParameterizedTest
    @CsvSource({
        "成功, 123",
        "success, 234",
        "成功, \"\"",
        "\"\", 234",
        "\"\", \"\"",
        "\"\", null",
        "null, null",
        "null, 234"
    })
    void testSuccess(String msg, String data) {
        BaseResult actual = BaseResult.success(msg, data);
        assertEquals(BaseResultCodeEnums.SUCCESS.getCode(), actual.getCode());
        assertEquals(msg, actual.getMessage());
        assertEquals(data, actual.getData());
    }

    @ParameterizedTest
    @EnumSource(BaseResultCodeEnums.class)
    void of_BaseResultCodeEnums(BaseResultCodeEnums codeEnums) {
        Object value = mock(Object.class);

        BaseResult actual = BaseResult.of(codeEnums, value);
        assertEquals(codeEnums.getCode(), actual.getCode());
        assertEquals(codeEnums.getMsg(), actual.getMessage());
        assertEquals(value, actual.getData());
    }

    @ParameterizedTest
    @NullSource
    void of_BaseResultCodeEnums_null(BaseResultCodeEnums codeEnums) {
        Object value = mock(Object.class);

        assertThrows(IllegalArgumentException.class, () -> BaseResult.of(codeEnums, value));
    }

    @ParameterizedTest
    @CsvSource({
        "0000, 123",
        "4000, 234",
        "5000, \"\"",
    })
    void of_BussinessException(String code, String msg) {
        BusinessException mockException = mock(BusinessException.class);
        doReturn(code).when(mockException).getCode();
        doReturn(msg).when(mockException).getMsg();

        BaseResult actual = BaseResult.of(mockException);
        assertEquals(code, actual.getCode());
        assertEquals(msg, actual.getMessage());
        assertEquals(null, actual.getData());
    }

    @ParameterizedTest
    @NullSource
    void of_BussinessException_null(BusinessException exception) {
        assertThrows(IllegalArgumentException.class, () -> BaseResult.of(exception));
    }

    @ParameterizedTest
    @CsvSource({
        "0000, true",
        "4000, false",
        "5000, false",
        "200, false",
        "\"\", false",
        "null, false",
    })
    void isSuccess(String code, boolean except) {
        BaseResult target = new BaseResult();
        target.setCode(code);

        assertEquals(except, target.isSuccess());

    }
}