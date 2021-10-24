package com.interview.assignment.validator;

import com.interview.assignment.annotation.AppId;
import com.interview.assignment.domain.Application;
import com.interview.assignment.service.ApplicationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AppIdValidator implements ConstraintValidator<AppId, String> {

  @Setter
  @Autowired
  private ApplicationService applicationService;

  @Override
  public boolean isValid(String appId, ConstraintValidatorContext constraintValidatorContext) {
    Application application = applicationService.findByAppId(appId);
    return null != application;
  }
}
