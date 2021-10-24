package com.interview.assignment.filter.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.exception.BusinessException;
import com.interview.assignment.request.ShortCodeQueryRequest;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortCodeRecordExistCheckFilterTest {

  private final ShortCodeRecordExistCheckFilter existCheckFilter = new ShortCodeRecordExistCheckFilter();

  @Test
  public void testShortCodeIsNull() {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");

    assertThrows(BusinessException.class, () -> existCheckFilter.filter(new Pair<>(request, null)));
  }

  @Test
  public void testShortCodeIsNotNull() {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");
    ShortCode shortCode = new ShortCode();
    boolean result = existCheckFilter.filter(new Pair<>(request, shortCode));
    assertTrue(result);
  }
}
