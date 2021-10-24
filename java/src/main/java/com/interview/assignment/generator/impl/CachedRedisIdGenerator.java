package com.interview.assignment.generator.impl;

import com.alibaba.fastjson.JSON;
import com.interview.assignment.constant.ConstantProviders;
import com.interview.assignment.generator.IdGenerator;
import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.model.IdCounter;
import com.interview.assignment.model.Segment;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import com.interview.assignment.util.MockedRedisUtil;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 通过redis的自增id来产生结果（这里只是模拟使用redis），而且为了提升效率，每次会获取一个批次的id数据，
 * 存储在redis中的model的数据会包含以下几个字段：
 *     base: 记录当前生成的数据的起始值；
 *     size: 记录每次生成的批次数据的大小
 * 每次生成一个批次的数据的时候，都会更新current=current+size，当然，每次获取批次中的数据时，会单独使用一个redis锁
 * 以防止并发处理请求。
 *
 * 为了提升请求的整体性能，这里采用了四个方法来获取id数据：
 * 1. 批量获取一个批次的id数据，并且缓存起来；
 * 2. 对缓存的id数据进行分片，在每次获取的时候，会随机选择一个尝试获取id数据，最多重试3次；
 * 3. 如果重试之后还是没有获取到id数据，则去redis中加载下一个批次的数据；
 * 4. 在每个分片中，可以存储多个Range（保存批次数据的model）的数据，当请求量比较大的时候，
 *    每个分片中的Range也会增多，从而保证绝大部分请求都是从缓存中加载的；
 * 5. 在从redis中读取数据的时候，使用的是base = base + size这种计算方式，而不是redis自带
 *    的incr()命令，这样能够保证在每次加载新的批次的时候，操作尽可能的小；
 */
@Primary
@Component
public class CachedRedisIdGenerator implements IdGenerator, InitializingBean {

  @Setter
  @Autowired
  private MockedRedisUtil redisClient;

  @Setter
  @Value("${cached.redis.id.generator.segment.size:2000}")
  private int segmentSize;

  @Setter
  @Value("${cached.redis.id.generator.segment.fetch.batch.size:1000}")
  private int batchSize;

  @Setter
  private volatile Object[] segmentLocks = null;
  @Setter
  private volatile Segment[] segments = null;

  @Override
  public GeneratedId generate(ShortCodeGenerateRequest request) {
    GeneratedId result = new GeneratedId();
    ThreadLocalRandom random = ThreadLocalRandom.current();

    Long generatedId;
    int idx, times = 0;
    do {
      idx = random.nextInt(segmentSize);
      generatedId = segments[idx].next();
      if (generatedId != null) {
        result.setId(generatedId);
        return result;
      }

      times++;
    } while (times < 3);

    // 加载Segment数据
    generatedId = loadSegment(idx);
    result.setId(generatedId);
    return result;
  }

  private Long loadSegment(int idx) {
    synchronized (segmentLocks[idx]) {
      String lockKey = ConstantProviders.getIdCounterLockKey();
      redisClient.lock(lockKey);
      try {
        return fetchNextBatch(idx);
      } finally {
        redisClient.unlock(lockKey);
      }
    }
  }

  private Long fetchNextBatch(int idx) {
    String key = ConstantProviders.getIdCounterKey();
    String str = redisClient.get(key);
    IdCounter idCounter;
    if (StringUtils.isBlank(str)) {
      idCounter = new IdCounter();
      idCounter.setBase(1);
    } else {
      idCounter = JSON.parseObject(str, IdCounter.class);
    }

    segments[idx].offer(idCounter.getBase(), batchSize);
    idCounter.setBase(idCounter.getBase() + batchSize);
    redisClient.set(key, JSON.toJSONString(idCounter));
    return segments[idx].next();
  }

  @Override
  public void afterPropertiesSet() {
    segments = new Segment[segmentSize];
    segmentLocks = new Object[segmentSize];
    for (int i = 0; i < segmentSize; i++) {
      segments[i] = new Segment();
      segmentLocks[i] = new Object();
    }
  }
}
