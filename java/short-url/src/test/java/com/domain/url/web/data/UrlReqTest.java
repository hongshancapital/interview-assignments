package com.domain.url.web.data;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlReqTest {

    @Test
    public void testUrlReq() {
        final UrlReq req1 = new UrlReq();
        Assertions.assertDoesNotThrow(req1::hashCode);
        Assertions.assertEquals(req1, new UrlReq());
        Assertions.assertNotEquals(req1, new UrlReq("test3"));
        req1.setUrl("test1");
        Assertions.assertDoesNotThrow(req1::hashCode);

        final UrlReq req2 = UrlReq.builder().url("test1").build();

        Assertions.assertEquals("test1", req1.getUrl());
        Assertions.assertTrue(req1.canEqual(req2));
        Assertions.assertNotEquals(req1, null);
        Assertions.assertNotEquals(req1, new Object());
        Assertions.assertNotEquals(new Object(), req1);
        Assertions.assertNotEquals(req1, new UrlReq());
        Assertions.assertNotEquals(req1, new UrlReq("test3"));
        Assertions.assertEquals(req1, req1);
        Assertions.assertEquals(req1, req2);
        Assertions.assertNotNull(req1.toString());
        Assertions.assertNotNull(UrlReq.builder().toString());
    }
}