package com.interview.assignment.util;

import com.interview.assignment.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Base62UtilTest {

  @Test
  public void testEncodeIllegalParam() {
    assertThrows(BusinessException.class, () -> Base62Util.encode(-10));
  }

  @Test
  public void testEncodeNumberIsZero() {
    String result = Base62Util.encode(0);
    assertEquals("l", result);
  }

  @Test
  public void testEncode() {
    String result = Base62Util.encode(123);
    assertEquals("yH", result);
  }

  @Test
  public void testDecodeStrIsEmpty() {
    assertThrows(BusinessException.class, () -> Base62Util.decode(""));
  }

  @Test
  public void testDecodeStrIsNotValidCharacter() {
    assertThrows(BusinessException.class, () -> Base62Util.decode("ssdf&"));
  }

  @Test
  public void testDecode() {
    long result = Base62Util.decode("yH");
    assertEquals(123, result);
  }
}
