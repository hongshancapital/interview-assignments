package com.interview.assignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SegmentTest {

  @Test
  public void testOffer() {
    Segment segment = new Segment();
    segment.offer(1, 1000);
    assertEquals(1, segment.queue.size());
  }

  @Test
  public void testNext() {
    Segment segment = new Segment();
    segment.offer(1, 1000);
    for (int i = 0; i < 1000; i++) {
      Long result = segment.next();
      assertEquals(i + 1, result);
    }

    Long result = segment.next();
    assertNull(result);
  }

  @Test
  public void testRangesNext() {
    Segment segment = new Segment();
    segment.offer(1, 1000);
    segment.offer(2001, 1000);
    for (int i = 0; i < 2000; i++) {
      Long result = segment.next();
      assertTrue((result >= 1 && result <= 1000) || (result >= 2001 && result <= 3000));
    }

    Long result = segment.next();
    assertNull(result);
  }
}
