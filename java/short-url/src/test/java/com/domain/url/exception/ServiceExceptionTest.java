package com.domain.url.exception;

import com.domain.url.service.codes.UrlServiceExceptionCode;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceExceptionTest {

    @Test
    public void testServiceException() {
        final ServiceException e1 = new ServiceException("test");
        e1.setCode(null);
        Assertions.assertDoesNotThrow(e1::hashCode);
        Assertions.assertNotEquals(e1, new ServiceException("test"));
        Assertions.assertNotEquals(e1, new ServiceException("test3"));
        e1.setCode("__UN_KNOWN__");
        Assertions.assertDoesNotThrow(e1::hashCode);
        Assertions.assertEquals(e1, new ServiceException("test"));
        Assertions.assertEquals(e1, new ServiceException("test3"));

        final ServiceException e2 = new ServiceException("test");
        final ServiceException e3 = new ServiceException(UrlServiceExceptionCode.__URL_E_0001__, "test", new RuntimeException("test"));
        Assertions.assertNotNull(e3.get());

        final ServiceException e4 = new ServiceException(UrlServiceExceptionCode.__URL_E_0001__);
        final ServiceException e5 = new ServiceException(UrlServiceExceptionCode.__URL_E_0001__, "test");

        Assertions.assertEquals("__UN_KNOWN__", e1.getCode());
        Assertions.assertTrue(e1.canEqual(e2));
        Assertions.assertNotEquals(e1, null);
        Assertions.assertNotEquals(e1, new Object());
        Assertions.assertNotEquals(new Object(), e1);
        Assertions.assertEquals(e1, e1);
        Assertions.assertEquals(e1, e2);
        Assertions.assertNotNull(e1.toString());
    }
}