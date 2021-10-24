package com.interview.assignment.filter.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.enums.DurationType;
import com.interview.assignment.exception.BusinessException;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ShortCodeExpiredCheckFilterTest {

  private final ShortCodeExpiredCheckFilter expiredCheckFilter = new ShortCodeExpiredCheckFilter();

  @Test
  public void testDurationTypeIsPermanent() {
    ShortCode shortCode = new ShortCode();
    shortCode.setDurationType(DurationType.PERMANENT.getType());
    boolean result = expiredCheckFilter.filter(new Pair<>(null, shortCode));
    assertTrue(result);
  }

  @Test
  public void testDurationTypeIsLimitedAndStartTimeIsNotReached() {
    ShortCode shortCode = new ShortCode();
    shortCode.setDurationType(DurationType.LIMITED_TIME.getType());
    Date now = new Date();
    Date startTime = new Date(now.getTime() + 1000);
    Date endTime = new Date(startTime.getTime() + 1000);
    shortCode.setStartTime(startTime);
    shortCode.setEndTime(endTime);
    assertThrows(BusinessException.class, () -> expiredCheckFilter.filter(new Pair<>(null, shortCode)));
  }

  @Test
  public void testDurationTypeIsLimitedAndEndTimeIsPassed() {
    ShortCode shortCode = new ShortCode();
    shortCode.setDurationType(DurationType.LIMITED_TIME.getType());
    Date now = new Date();
    Date startTime = new Date(now.getTime() - 2000);
    Date endTime = new Date(startTime.getTime() + 1000);
    shortCode.setStartTime(startTime);
    shortCode.setEndTime(endTime);
    assertThrows(BusinessException.class, () -> expiredCheckFilter.filter(new Pair<>(null, shortCode)));
  }

  @Test
  public void testDurationTypeIsLimitedAndTimeIsBetweenTimeRange() {
    ShortCode shortCode = new ShortCode();
    shortCode.setDurationType(DurationType.LIMITED_TIME.getType());
    Date now = new Date();
    Date startTime = new Date(now.getTime() - 2000);
    Date endTime = new Date(startTime.getTime() + 3000);
    shortCode.setStartTime(startTime);
    shortCode.setEndTime(endTime);
    boolean result = expiredCheckFilter.filter(new Pair<>(null, shortCode));
    assertTrue(result);
  }

}
