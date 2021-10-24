package com.interview.assignment.generator.impl;

import com.interview.assignment.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VersionZeroShortCodeHandlerTest {

  private final VersionZeroShortCodeHandler shortCodeHandler = new VersionZeroShortCodeHandler();

  @Test
  public void testGenerateIdIsNull() {
    assertThrows(BusinessException.class, () -> shortCodeHandler.generate(null));
  }
  @Test
  public void testGenerateIdIsLowerOrEqualToZero() {
    assertThrows(BusinessException.class, () -> shortCodeHandler.generate(-1L));
  }

  @Test
  public void testGenerate() {
    String result = shortCodeHandler.generate(123L);
    assertEquals("yHy", result);
  }

  @Test
  public void testCheckCodeIsBlank() {
    assertThrows(BusinessException.class, () -> shortCodeHandler.check("  "));
  }

  @Test
  public void testCheckCode() {
    boolean result = shortCodeHandler.check("yHy");
    assertTrue(result);
  }

}
