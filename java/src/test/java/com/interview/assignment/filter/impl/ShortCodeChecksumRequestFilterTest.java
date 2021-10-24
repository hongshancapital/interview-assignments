package com.interview.assignment.filter.impl;

import com.interview.assignment.generator.ShortCodeHandler;
import com.interview.assignment.request.ShortCodeQueryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ShortCodeChecksumRequestFilterTest {

  private final ShortCodeHandler shortCodeHandler = Mockito.mock(ShortCodeHandler.class);

  private final ShortCodeChecksumRequestFilter shortCodeChecksumRequestFilter = new ShortCodeChecksumRequestFilter();

  @BeforeEach
  public void before() {
    shortCodeChecksumRequestFilter.setShortCodeHandler(shortCodeHandler);
  }

  @Test
  public void testCheckFalse() {
    when(shortCodeHandler.check(any())).thenReturn(false);

    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");
    boolean result = shortCodeChecksumRequestFilter.filter(request);
    assertFalse(result);
  }

  @Test
  public void testCheckTrue() {
    when(shortCodeHandler.check(any())).thenReturn(true);

    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");
    boolean result = shortCodeChecksumRequestFilter.filter(request);
    assertTrue(result);
  }
}
