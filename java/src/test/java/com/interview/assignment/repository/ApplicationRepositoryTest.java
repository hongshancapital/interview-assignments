package com.interview.assignment.repository;

import com.interview.assignment.domain.Application;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ApplicationRepositoryTest {

  ApplicationRepository applicationRepository = new ApplicationRepository();

  @Test
  public void testFindByAppIdAppIdIsNull() {
    Application result = applicationRepository.findByAppId(null);
    assertNull(result);
  }

  @Test
  public void testFindByAppIdAppIdIsBlank() {
    Application result = applicationRepository.findByAppId("  ");
    assertNull(result);
  }

  @Test
  public void testFindByAppId() {
    Application result = applicationRepository.findByAppId("test-1");
    assertNull(result);
  }

  @Test
  public void testInit() {
    applicationRepository.init();
    Application result = applicationRepository.findByAppId("test");
    assertEquals("test", result.getAppId());
  }

}
