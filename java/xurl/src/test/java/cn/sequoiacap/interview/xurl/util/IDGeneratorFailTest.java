package cn.sequoiacap.interview.xurl.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("error")
public class IDGeneratorFailTest {
  @Test
  public void testGenerate_Fail() {
    try {
      IDGenerator.generate();
      Assertions.fail("should fail");
    } catch (IDGenerator.OutOfIndexError e) {
      Assertions.assertTrue(e.getMessage().contains("machine"));
    }
  }

  @AfterAll
  public static void cleanup() {
    IDGenerator.clear();
  }
}
