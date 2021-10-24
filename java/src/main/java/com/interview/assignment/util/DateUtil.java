package com.interview.assignment.util;

import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.exception.BusinessException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  private DateUtil() {}

  public static String format(Date date, String pattern) {
    if (date == null || null == pattern || pattern.trim().isEmpty()) {
      throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
    }

    SimpleDateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }
}
