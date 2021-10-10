package com.sequoiacap.business.process.manager.util;

import org.springframework.util.CollectionUtils;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.Set;
/**
 * @ClassName: MyValidationUtils
 * @Description: 手动调用api方法校验对象
 * @Author: xulong.wang
 * @Date 10/9/2021
 * @Version 1.0
 */
public class MyValidationUtils {

  public static void validate(@Valid Object user) {
    Set<ConstraintViolation<@Valid Object>> validateSet = Validation.buildDefaultValidatorFactory()
            .getValidator()
            .validate(user, new Class[0]);
    if (!CollectionUtils.isEmpty(validateSet)) {
      String messages = validateSet.stream()
              .map(ConstraintViolation::getMessage)
              .reduce((m1, m2) -> m1 + "；" + m2)
              .orElse("参数输入有误！");
      throw new IllegalArgumentException(messages);
    }
  }

}

