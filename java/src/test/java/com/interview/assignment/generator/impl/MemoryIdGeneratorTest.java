package com.interview.assignment.generator.impl;

import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemoryIdGeneratorTest {

  private final MemoryIdGenerator memoryIdGenerator = new MemoryIdGenerator();

  @Test
  public void testGenerate() {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    GeneratedId generatedId = memoryIdGenerator.generate(request);
    assertEquals(1, generatedId.getId());
  }

}
