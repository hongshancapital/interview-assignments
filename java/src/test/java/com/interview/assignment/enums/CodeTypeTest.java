package com.interview.assignment.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CodeTypeTest {
  @Test
  public void testGet() {
    assertEquals("短链跳转", CodeType.URL_REDIRECT.getDesc());
    assertEquals("url_redirect", CodeType.URL_REDIRECT.getType());
  }

  @Test
  public void testStaticGet() {
    assertEquals(CodeType.URL_REDIRECT, CodeType.get("url_redirect"));
  }

  @Test
  public void testStaticGetNull() {
    assertNull(DurationType.get("url_redirect1"));
  }
}
