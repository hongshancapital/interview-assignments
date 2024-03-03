package com.tb.link.qatest.infrastructure;

import com.tb.link.infrastructure.exception.TbRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * @author andy.lhc
 * @date 2022/4/17 10:41
 */
public class TbRuntimeExceptionTest {

    @Test
    public void testExceptionErrorCode() {
        String str = "abc";
        if (StringUtils.isNotBlank(str)) {
            TbRuntimeException tb = new TbRuntimeException("notNull", "notNull");
            Assertions.assertEquals(tb.getErrorCode(), "notNull");
            Assertions.assertEquals(tb.getErrorMsg(), "notNull");

        }
    }

    @Test
    public void testThrowException() {
        String str = "abc";
        Assertions.assertThrows(TbRuntimeException.class, () -> {
            if (StringUtils.isNotBlank(str)) {
                throw new TbRuntimeException("notNull", "notNull");
            }
        });
        Assertions.assertDoesNotThrow(() -> {
            if (StringUtils.isBlank(str)) {
                throw new TbRuntimeException("notNull", "notNull");
            }
        });
    }
}
