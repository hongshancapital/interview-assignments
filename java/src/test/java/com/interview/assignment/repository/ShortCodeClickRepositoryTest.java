package com.interview.assignment.repository;

import com.interview.assignment.domain.ShortCodeClick;
import com.interview.assignment.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShortCodeClickRepositoryTest {

  private final ShortCodeClickRepository shortCodeClickRepository = new ShortCodeClickRepository();

  @Test
  public void testRecordParamIsBlank() {
    assertThrows(BusinessException.class, () -> shortCodeClickRepository.record("", "", "", ""));
  }

  @Test
  public void testRecord() {
    shortCodeClickRepository.record("test", "test", "2021", "10");
    assertFalse(shortCodeClickRepository.timeRegionMap.isEmpty());
    assertEquals(1, shortCodeClickRepository.timeRegionMap.size());
    Map.Entry<String, ShortCodeClick> entry = shortCodeClickRepository.timeRegionMap.entrySet().iterator().next();
    assertEquals("test", entry.getValue().getAppId());
    assertEquals(1, entry.getValue().getTimes());
  }

}
