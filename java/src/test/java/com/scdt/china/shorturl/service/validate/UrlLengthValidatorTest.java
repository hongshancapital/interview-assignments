package com.scdt.china.shorturl.service.validate;

import com.scdt.china.shorturl.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlLengthValidatorTest {

    @Test
    void validate() {
        UrlLengthValidator urlLengthValidator = new UrlLengthValidator();
        // 空URL
        Assertions.assertThrows(BusinessException.class, () -> urlLengthValidator.validate(""));
        // 超长URL
        Assertions.assertThrows(BusinessException.class, () -> {
            String longString = String.format("%09999d", 99);
            urlLengthValidator.validate(longString);
        });
        // 正常
        Assertions.assertDoesNotThrow(() -> urlLengthValidator.validate("https://www.baidu.com"));


    }
}