package com.interview.assignment.generator.impl;

import com.interview.assignment.constant.ConstantVariables;
import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.exception.BusinessException;
import com.interview.assignment.generator.ShortCodeHandler;
import com.interview.assignment.util.Base62Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 *    首先会把传入的id编码为一个base62的字符串，然后额外增加一个字符，用于记录这个code的版本，以及校验和。
 * 具体的记录方式是，由于一个字符最多表示62个数字，对应的最多就是5个字节，因而前面两个字节用于表示版本号，后面
 * 三个字节用于计算校验和。
 *    由于第一个版本值为0，因而计算这个字符时，该字符的值与计算得到的checksum的值是一样的。这里增加版本号
 * 的原因是后续对code进行改版比较方便，也可以做版本兼容。
 *    由于三个字节最多表示8个数字，也就是0~7，因而校验和采用简单的计算方式就是对8取余。这里加校验和的目的是，
 * 防止调用方随意刷恶意的短码，造成请求都打到后面服务，比如数据库、redis中。
 */
@Component
public class VersionZeroShortCodeHandler implements ShortCodeHandler {
  @Override
  public String generate(Long id) {
    if (id == null || id <= 0) {
      throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
    }

    String str = Base62Util.encode(id);
    int checksum = computeChecksum(str);
    String suffix = Base62Util.encode(checksum);  // 第一个版本的值都是0，因而表示版本的两位不需要加。这里编码后只会得到一个字符
    return str + suffix;
  }

  private int computeChecksum(String str) {
    int sum = 0;
    for (int i = 0; i < str.length(); i++) {
      sum += str.charAt(i);
    }

    return sum % ConstantVariables.CHECK_SUM_BASE;
  }

  @Override
  public boolean check(String shortCode) {
    if (StringUtils.isBlank(shortCode)) {
      throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
    }

    String prefix = shortCode.substring(0, shortCode.length() - 1);
    String suffix = shortCode.substring(shortCode.length() - 1);
    int checksum = computeChecksum(prefix);
    return Base62Util.encode(checksum).equals(suffix);
  }
}
