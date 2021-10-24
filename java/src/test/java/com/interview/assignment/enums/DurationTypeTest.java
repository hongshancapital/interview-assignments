package com.interview.assignment.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DurationTypeTest {
  @Test
  public void testGet() {
    assertEquals("永久", DurationType.PERMANENT.getDesc());
    assertEquals("permanent", DurationType.PERMANENT.getType());
  }

  @Test
  public void testStaticGet() {
    assertEquals(DurationType.PERMANENT, DurationType.get("permanent"));
  }

  @Test
  public void testStaticGetNull() {
    assertNull(DurationType.get("permanents"));
  }
}
