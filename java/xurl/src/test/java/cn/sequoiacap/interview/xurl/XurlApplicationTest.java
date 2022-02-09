package cn.sequoiacap.interview.xurl;

import cn.sequoiacap.interview.xurl.util.SpringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XurlApplicationTest {

  @Test
  void testSpringContextSet_Ok() {
    Assertions.assertTrue(SpringUtil.isSet());
  }
}
