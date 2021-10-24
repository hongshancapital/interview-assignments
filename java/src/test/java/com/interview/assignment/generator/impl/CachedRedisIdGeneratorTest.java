package com.interview.assignment.generator.impl;

import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.model.Segment;
import com.interview.assignment.util.MockedRedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CachedRedisIdGeneratorTest {

  private final MockedRedisUtil redisClient = Mockito.mock(MockedRedisUtil.class);
  private final Segment segment = Mockito.mock(Segment.class);

  private final CachedRedisIdGenerator generator = new CachedRedisIdGenerator();

  @BeforeEach
  public void before() {
    generator.setBatchSize(1000);
    generator.setSegmentSize(16);


    Object[] segmentLocks = new Object[16];
    Segment[] segments = new Segment[16];
    for (int i = 0; i < segments.length; i++) {
      segments[i] = segment;
      segmentLocks[i] = new Object();
    }

    generator.setRedisClient(redisClient);
    generator.setSegments(segments);
    generator.setSegmentLocks(segmentLocks);
  }

  @Test
  public void testAfterPropertySet() {
    CachedRedisIdGenerator generator = new CachedRedisIdGenerator();
    generator.setSegmentSize(16);
    generator.setBatchSize(1000);
    generator.afterPropertiesSet();
  }

  @Test
  public void testGeneratedIdIsNotNull() {
    when(segment.next()).thenReturn(1L);

    GeneratedId result = generator.generate(null);
    assertEquals(1L, result.getId());
  }

  @Test
  public void testRetryedGeneratedIdIsNotNull() {
    when(segment.next()).thenReturn(null, 1L);

    GeneratedId result = generator.generate(null);
    assertEquals(1L, result.getId());
  }


  @Test
  public void testRetryAndLoadSegmentAndRedisIsBlank() {
    when(segment.next()).thenReturn(null, null, null, 1L);
    when(redisClient.get(any())).thenReturn(null);

    GeneratedId result = generator.generate(null);
    assertEquals(1L, result.getId());
    verify(redisClient, times(1)).lock(any());
    verify(redisClient, times(1)).unlock(any());
    verify(redisClient, times(1)).set(any(), any());
  }

  @Test
  public void testRetryAndLoadSegmentAndRedisIsNotBlank() {
    when(segment.next()).thenReturn(null, null, null, 1L);
    when(redisClient.get(any())).thenReturn("{\"base\": 1}");

    GeneratedId result = generator.generate(null);
    assertEquals(1L, result.getId());
    verify(redisClient, times(1)).lock(any());
    verify(redisClient, times(1)).unlock(any());
    verify(redisClient, times(1)).set(any(), any());
  }

}
