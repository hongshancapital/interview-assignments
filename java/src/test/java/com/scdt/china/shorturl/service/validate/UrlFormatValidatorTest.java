package com.scdt.china.shorturl.service.validate;

import com.scdt.china.shorturl.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlFormatValidatorTest {

    @Test
    void validate() {
        UrlFormatValidator urlFormatValidator = new UrlFormatValidator();
        // 错误格式的URL
        Assertions.assertThrows(BusinessException.class, () -> urlFormatValidator.validate("dsadasda"));
        // 不支持ftp协议
        Assertions.assertThrows(BusinessException.class, () -> urlFormatValidator.validate("ftp://dsadasda"));
        // 正常
        Assertions.assertDoesNotThrow(() -> urlFormatValidator.validate("https://www.baidu.com"));
    }

}