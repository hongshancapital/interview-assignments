package com.interview.assignment.filter.impl;

import com.interview.assignment.enums.DurationType;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortCodeDurationFilterTest {

  private final ShortCodeDurationFilter durationFilter = new ShortCodeDurationFilter();

  @Test
  public void testDurationTypeIsNull() {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    boolean result = durationFilter.filter(request);
    assertTrue(result);
  }

  @Test
  public void testDurationTypeIsPermanent() {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    request.setDurationType(DurationType.PERMANENT);
    boolean result = durationFilter.filter(request);
    assertTrue(result);
  }

  @Test
  public void testDurationTypeIsLimitedAndStartTimeIsNull() {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    request.setDurationType(DurationType.LIMITED_TIME);
    boolean result = durationFilter.filter(request);
    assertFalse(result);
  }

  @Test
  public void testDurationTypeIsLimitedAndEndTimeIsNull() {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    request.setDurationType(DurationType.LIMITED_TIME);
    request.setStartTime(new Date());
    boolean result = durationFilter.filter(request);
    assertFalse(result);
  }

  @Test
  public void testDurationTypeIsLimitedAndStartTimeIsGreaterThanEndTime() {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    request.setDurationType(DurationType.LIMITED_TIME);
    Date startTime = new Date();
    request.setStartTime(startTime);
    request.setEndTime(new Date(startTime.getTime() - 1));
    boolean result = durationFilter.filter(request);
    assertFalse(result);
  }

  @Test
  public void testFilterPass() {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    request.setDurationType(DurationType.LIMITED_TIME);
    Date startTime = new Date();
    request.setStartTime(startTime);
    request.setEndTime(new Date(startTime.getTime() + 1));
    boolean result = durationFilter.filter(request);
    assertTrue(result);
  }

}
