package com.interview.assignment.generator.impl;

import com.interview.assignment.generator.IdGenerator;
import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 在内存中生成一个id，这个generator只适用于单机，并且不需要持久化已生成id数据的场景，在当前实例重启之后，数据将会消失
 */
@Component
public class MemoryIdGenerator implements IdGenerator {

  private static final AtomicLong base = new AtomicLong(1);

  @Override
  public GeneratedId generate(ShortCodeGenerateRequest request) {
    long id = base.getAndIncrement();
    GeneratedId result = new GeneratedId();
    result.setId(id);
    return result;
  }
}
