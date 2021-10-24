package com.interview.assignment.util;

import com.interview.assignment.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateUtilTest {
  @Test
  public void testFormatDateIsNull() {
    assertThrows(BusinessException.class, () -> DateUtil.format(null, "yyyy"));
  }

  @Test
  public void testFormatDatePatternIsNull() {
    assertThrows(BusinessException.class, () -> DateUtil.format(getDate(), null));
  }

  @Test
  public void testFormatDatePatternIsEmpty() {
    assertThrows(BusinessException.class, () -> DateUtil.format(getDate(), ""));
  }

  @Test
  public void testFormatDatePatternIsBlank() {
    assertThrows(BusinessException.class, () -> DateUtil.format(getDate(), "  "));
  }

  @Test
  public void testFormatDatePatternIsNotValid() {
    assertThrows(IllegalArgumentException.class, () -> DateUtil.format(getDate(), "fdafafd"));
  }

  @Test
  public void testDateFormat() {
    String result = DateUtil.format(getDate(), "yyyy-MM-dd HH:mm:ss");
    assertEquals("2021-10-23 08:55:00", result);
  }

  private Date getDate() {
    LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 23, 8, 55);
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
