package com.url.transform.generator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xufei
 * @Description
 * @date 2021/12/10 2:36 PM
 **/
class StringGeneratorRandomTest {

  @org.junit.jupiter.api.Test
  void generate() {
    assertEquals(false, new StringGeneratorRandom().generate("111").isEmpty());
  }

}