package com.zhucan.domain.infrastructure.test.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author zhuCan
 * @description 字符长度比对器
 * @since 2022/4/3 18:06
 */
public class StringLengthMatcher extends TypeSafeMatcher<String> {

  private final int length;

  public StringLengthMatcher(int length) {
    this.length = length;

    // 不可使用非自然数长度
    if (length < 0) {
      throw new IllegalArgumentException("invalid length");
    }
  }

  @Override
  protected boolean matchesSafely(String s) {
    return s != null && s.length() == this.length;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("the length of Text should be ").appendValue(length);
  }

  public static Matcher<String> length(int length) {
    return new StringLengthMatcher(length);
  }

  public void describeMismatchSafely(String item, Description mismatchDescription) {
    mismatchDescription.appendText(" Text was \"")
        .appendText(item)
        .appendText(" and length was ")
        .appendText(item == null ? "null" : String.valueOf(item.length()));
  }
}
