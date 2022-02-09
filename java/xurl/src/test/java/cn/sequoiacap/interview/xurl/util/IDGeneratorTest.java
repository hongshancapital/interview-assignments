package cn.sequoiacap.interview.xurl.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("default")
public class IDGeneratorTest {

  @Test
  public void testCommonGenerate_Ok() {
    try {
      long id = IDGenerator.generate();
      Assertions.assertTrue(id > 0);
      Assertions.assertTrue(id < (Math.pow(2, 48)));
    } catch (IDGenerator.OutOfIndexError e) {
      Assertions.fail("no exception should exists");
    }
  }

  @Test
  public void testGenerateIncrement_Ok() {
    try {
      long id1 = IDGenerator.generate();
      long id2 = IDGenerator.generate();
      Assertions.assertTrue(id1 < id2);
    } catch (IDGenerator.OutOfIndexError e) {
      Assertions.fail("no exception should exists");
    }
  }

  @Test
  public void testGenerateInParallel_Ok() {
    int parallel = 8;
    Long[] res = new Long[parallel];
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(parallel);
    IntStream.range(0, parallel)
        .forEach(
            i ->
                executor.execute(
                    () -> {
                      try {
                        res[i] = IDGenerator.generate();
                      } catch (IDGenerator.OutOfIndexError e) {
                        res[i] = -1L;
                      }
                    }));
    executor.shutdown();
    try {
      boolean success = executor.awaitTermination(3, TimeUnit.SECONDS);
      Assertions.assertTrue(success);
    } catch (InterruptedException e) {
      Assertions.fail("unexpected termination");
    }
    Set<Long> resSet = new HashSet<>(Arrays.asList(res));
    Assertions.assertEquals(0L, Arrays.stream(res).filter(id -> id == -1).count());
    Assertions.assertEquals(parallel, resSet.size());
  }

  @AfterAll
  public static void cleanup() {
    IDGenerator.clear();
  }
}
