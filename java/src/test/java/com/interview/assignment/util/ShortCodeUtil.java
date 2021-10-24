package com.interview.assignment.util;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.enums.CodeType;
import com.interview.assignment.enums.DurationType;

import java.util.Date;

public class ShortCodeUtil {
  private ShortCodeUtil() {}

  public static ShortCode getShortCode() {
    ShortCode shortCode = new ShortCode();
    shortCode.setId(1L);
    shortCode.setCode("ya3");
    shortCode.setAppId("test");
    shortCode.setUpdateTime(new Date());
    shortCode.setCreateTime(new Date());
    shortCode.setUrl("http://www.baidu.com");
    shortCode.setType(CodeType.URL_REDIRECT.getType());
    shortCode.setDurationType(DurationType.PERMANENT.getType());
    return shortCode;
  }
}
