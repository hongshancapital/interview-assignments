package com.interview.assignment.service.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.repository.ShortCodeClickRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ShortCodeClickServiceTest {

  private final ShortCodeClickRepository shortCodeClickRepository = Mockito.mock(ShortCodeClickRepository.class);

  private final ShortCodeClickServiceImpl clickService = new ShortCodeClickServiceImpl();

  @BeforeEach
  public void before() {
    clickService.setShortCodeClickRepository(shortCodeClickRepository);
  }

  @Test
  public void testRecord() {
    ShortCode shortCode = new ShortCode();
    shortCode.setAppId("test-app-id");
    shortCode.setCode("test-code");
    clickService.record(shortCode);
    verify(shortCodeClickRepository, times(1)).record(any(), any(), any(), any());
  }

}
