package com.url.transform.controller;

import com.url.transform.generator.StringGeneratorRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xufei
 * @Description
 * @date 2021/12/10 2:53 PM
 **/
class TestControllerTest {

  @Test
  void transFormUrl() {
    assertEquals("0000", new TestController().transFormUrl("111").getCode());
  }

  @Test
  void transFormShorter() {
    assertEquals("500", new TestController().transFormShorter("111").getCode());
  }
}