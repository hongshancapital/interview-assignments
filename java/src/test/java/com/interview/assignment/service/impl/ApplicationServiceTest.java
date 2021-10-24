package com.interview.assignment.service.impl;

import com.interview.assignment.domain.Application;
import com.interview.assignment.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ApplicationServiceTest {

  private final ApplicationRepository applicationRepository = Mockito.mock(ApplicationRepository.class);

  private final ApplicationServiceImpl applicationService = new ApplicationServiceImpl();

  @BeforeEach
  public void before() {
    applicationService.setApplicationRepository(applicationRepository);
  }

  @Test
  public void testFindByAppId() {
    Application application = new Application();
    application.setAppId("test");
    when(applicationRepository.findByAppId("test")).thenReturn(application);

    Application result = applicationService.findByAppId("test");
    assertEquals(result.getAppId(), application.getAppId());
  }

}
