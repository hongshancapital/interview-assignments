package com.interview.assignment.validator;

import com.interview.assignment.domain.Application;
import com.interview.assignment.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AppIdValidatorTest {

  private final ApplicationService applicationService = Mockito.mock(ApplicationService.class);

  private final AppIdValidator validator = new AppIdValidator();

  @BeforeEach
  public void before() {
    validator.setApplicationService(applicationService);
  }

  @Test
  public void testIsValidApplicationIsNull() {
    when(applicationService.findByAppId(any())).thenReturn(null);

    boolean result = validator.isValid("test", null);
    assertFalse(result);
  }

  @Test
  public void testIsValidApplicationIsNotNull() {
    when(applicationService.findByAppId(any())).thenReturn(new Application());

    boolean result = validator.isValid("test", null);
    assertTrue(result);
  }
}
