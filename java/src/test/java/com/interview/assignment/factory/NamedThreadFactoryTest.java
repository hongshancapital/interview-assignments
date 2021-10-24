package com.interview.assignment.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NamedThreadFactoryTest {

  @Test
  public void testNewThread() {
    NamedThreadFactory factory = new NamedThreadFactory("test");
    Thread thread = factory.newThread(() -> {});
    assertEquals("test-1", thread.getName());
  }

}
