package com.hongshan.shorturl.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 * @version: 1.0
 */
public class ExpireTimeValidator implements ConstraintValidator<ExpireTimeValidator.ExpireTimeConstraint, Long> {

    @Override
    public boolean isValid(Long expireAt, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(expireAt) || expireAt > System.currentTimeMillis();
    }


    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = ExpireTimeValidator.class)
    public @interface ExpireTimeConstraint {
        String message() default "过期时间不能晚于当前时间";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}
