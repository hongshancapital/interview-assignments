package com.zhucan.domain.web;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 18:47
 */
public class IndexControllerTests {

  @Test
  public void indexTest() {
    assertThat(new IndexController().apiDocs(), equalTo("redirect:/swagger-ui.html"));
  }
}
