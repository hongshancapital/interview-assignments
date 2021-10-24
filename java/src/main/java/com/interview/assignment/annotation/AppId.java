package com.interview.assignment.annotation;

import com.interview.assignment.validator.AppIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
  validatedBy = AppIdValidator.class
)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AppId {

  /**
   * 返回的异常信息
   */
  String message() default "appId not valid!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
