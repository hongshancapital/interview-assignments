package com.interview.assignment.repository;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.util.ShortCodeUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ShortCodeRepositoryTest {

  private final ShortCodeRepository shortCodeRepository = new ShortCodeRepository();

  @Test
  public void testSaveIdIsNull() {
    ShortCode shortCode = Mockito.mock(ShortCode.class);
    when(shortCode.getId()).thenReturn(null);
    when(shortCode.getCode()).thenReturn("test");

    shortCodeRepository.save(shortCode);
    verify(shortCode, times(1)).setId(any());
    verify(shortCode, times(1)).setUpdateTime(any());
  }

  @Test
  public void testSaveIdIsLowerOrEqualToZero() {
    ShortCode shortCode = Mockito.mock(ShortCode.class);
    when(shortCode.getId()).thenReturn(-1L);
    when(shortCode.getCode()).thenReturn("test");

    shortCodeRepository.save(shortCode);
    verify(shortCode, times(1)).setId(any());
    verify(shortCode, times(1)).setUpdateTime(any());
  }

  @Test
  public void testSaveIdIsGreaterThanZero() {
    ShortCode shortCode = Mockito.mock(ShortCode.class);
    when(shortCode.getId()).thenReturn(1L);
    when(shortCode.getCode()).thenReturn("test");

    shortCodeRepository.save(shortCode);
    verify(shortCode, times(0)).setId(any());
    verify(shortCode, times(1)).setUpdateTime(any());
  }

  @Test
  public void testFindByCodeCodeIsNull() {
    ShortCode result = shortCodeRepository.findByCode(" ");
    assertNull(result);
  }

  @Test
  public void testFindByCode() {
    ShortCode shortCode = ShortCodeUtil.getShortCode();
    shortCodeRepository.save(shortCode);
    ShortCode result = shortCodeRepository.findByCode("ya3");
    assertEquals("ya3", result.getCode());
  }

}
