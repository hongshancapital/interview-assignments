package com.domain.url.web.data;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonResultTest {

    @Test
    public void testJsonResult() {
        final JsonResult<String> result1 = new JsonResult<>();
        Assertions.assertDoesNotThrow(result1::hashCode);
        Assertions.assertEquals(result1, new JsonResult<>());
        Assertions.assertNotEquals(result1, JsonResult.ok());

        result1.setStatus("ok");
        Assertions.assertDoesNotThrow(result1::hashCode);
        Assertions.assertNotEquals(result1, new JsonResult<>());
        Assertions.assertNotEquals(result1, JsonResult.ok());

        result1.setData("testData");
        Assertions.assertDoesNotThrow(result1::hashCode);
        Assertions.assertNotEquals(result1, new JsonResult<>());
        Assertions.assertNotEquals(result1, JsonResult.ok());

        result1.setMsg("testMsg");
        Assertions.assertDoesNotThrow(result1::hashCode);
        Assertions.assertNotEquals(result1, new JsonResult<>());
        Assertions.assertNotEquals(result1, JsonResult.ok());

        final JsonResult<String> result2 = JsonResult.ok("testData");
        final JsonResult<?> result3 = JsonResult.fail("fail", "testMsg");
        Assertions.assertThrows(RuntimeException.class, () -> JsonResult.fail("ok", "testMsg"));
        final JsonResult<?> result5 = JsonResult.fail(null, "testMsg");

        Assertions.assertEquals("ok", result1.getStatus());
        Assertions.assertNotNull(result1.getMsg());
        Assertions.assertTrue(result1.getTimestamp() > 0);
        Assertions.assertNotNull(result2.getData());

        Assertions.assertTrue(result1.canEqual(result2));
        Assertions.assertNotEquals(result1, null);
        Assertions.assertNotEquals(result1, new Object());
        Assertions.assertNotEquals(new Object(), result1);
        Assertions.assertNotEquals(result1, result3);
        Assertions.assertNotEquals(result1, JsonResult.ok());
        Assertions.assertNotEquals(result1, result2);
        Assertions.assertNotEquals(result1, result3);
        Assertions.assertEquals(result1, result1);
        Assertions.assertNotNull(result1.toString());
    }
}