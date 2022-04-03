package com.zhucan.domain.infrastructure.test.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 18:40
 */
public class StringLengthMatcherTests {

  /**
   * 测试文字长度非自然数
   */
  @Test
  public void matcherInitializeTest() {
    try {
      StringLengthMatcher.length(-1);
    } catch (Exception e) {
      assertThat(e instanceof IllegalArgumentException, equalTo(true));
    }
  }

  @Test
  public void matchTest() {

    StringLengthMatcher stringMatcher = new StringLengthMatcher(3);

    stringMatcher.describeTo(new Description.NullDescription());

    stringMatcher.describeMismatch("1233", new Description.NullDescription());

    stringMatcher.describeMismatch(null, new Description.NullDescription());


    assertThat(stringMatcher.matchesSafely("22"), equalTo(false));
    assertThat(stringMatcher.matchesSafely("333"), equalTo(true));
    // 长度比对验证
    assertThat(stringMatcher.matches("123"), equalTo(true));

    assertThat(stringMatcher.matches(null), equalTo(false));


  }
}
