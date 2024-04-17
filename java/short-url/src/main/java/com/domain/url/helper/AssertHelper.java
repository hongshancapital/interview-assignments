package com.domain.url.helper;

import com.domain.url.exception.IExceptionCode;
import com.domain.url.exception.ServiceException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 断言辅助类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertHelper {
    public static void notBlank(String str, IExceptionCode code, Object... args) throws ServiceException {
        if (str == null || str.length() == 0) {
            throw new ServiceException(code, args);
        }
    }

    public static void notNull(Object o, IExceptionCode code, Object... args) throws ServiceException {
        if (o == null) {
            throw new ServiceException(code, args);
        }
    }

    public static void isTrue(boolean condition, IExceptionCode code, Object... args) throws ServiceException {
        if (!condition) {
            throw new ServiceException(code, args);
        }
    }

    public static void notEmpty(Collection<?> coll, IExceptionCode code, Object... args) throws ServiceException {
        if (coll == null || coll.isEmpty()) {
            throw new ServiceException(code, args);
        }
    }
}
