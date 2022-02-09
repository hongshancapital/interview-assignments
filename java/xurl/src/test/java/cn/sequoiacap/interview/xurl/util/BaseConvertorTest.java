package cn.sequoiacap.interview.xurl.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseConvertorTest {
  @Test
  public void testTo62_Ok() {
    long a = 0;
    Assertions.assertEquals("0", BaseConvertor.to62(a));

    a = 61;
    Assertions.assertEquals("Z", BaseConvertor.to62(a));

    a = 432748239;
    Assertions.assertEquals("thLAj", BaseConvertor.to62(a));

    a = (long) (Math.pow(62, 8) - 1);
    Assertions.assertEquals("ZZZZZZZZ", BaseConvertor.to62(a));
  }

  @Test
  public void testTo62_Fail() {
    long a = -1;
    try {
      BaseConvertor.to62(a);
      Assertions.fail();
    } catch (NumberFormatException e) {
      Assertions.assertTrue(e.getMessage().contains("range"));
    }

    a = (long) Math.pow(62, 8);
    try {
      BaseConvertor.to62(a);
      Assertions.fail();
    } catch (NumberFormatException e) {
      Assertions.assertTrue(e.getMessage().contains("range"));
    }
  }

  @Test
  public void testFrom62_Ok() {
    String a = "0";
    Assertions.assertEquals(0, BaseConvertor.from62(a));

    a = "Z";
    Assertions.assertEquals(61, BaseConvertor.from62(a));

    a = "thLAj";
    Assertions.assertEquals(432748239, BaseConvertor.from62(a));

    a = "ZZZZZZZZ";
    Assertions.assertEquals((long) (Math.pow(62, 8) - 1), BaseConvertor.from62(a));
  }

  @Test
  public void testFrom62_Fail() {
    String a = "-1";
    try {
      BaseConvertor.from62(a);
      Assertions.fail();
    } catch (NumberFormatException e) {
      Assertions.assertTrue(e.getMessage().contains("invalid"));
    }

    a = "123456789";
    try {
      BaseConvertor.from62(a);
      Assertions.fail();
    } catch (NumberFormatException e) {
      Assertions.assertTrue(e.getMessage().contains("range"));
    }
  }
}
