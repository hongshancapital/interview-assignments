package com.interview.assignment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

@Setter
@Getter
public class Segment {


  Queue<Range> queue = new ConcurrentLinkedQueue<>();

  /**
   * 每次都从队列头部获取数据，而且需要注意的是，这里取到的一个Range数据
   * 对于每个线程而言，都是独占的，另外有一个线程尝试读取的时候回返回空，
   * 那么调用方就会再去进行一次加载，从而保证每个Segment中有足够多的Range，
   * 也就保证了服务的性能
   *
   * @return 能够获取到的id
   */
  public Long next() {
    do {
      Range range = queue.poll();
      if (range != null) {
        Long next = range.next();
        if (null != next) {
          queue.offer(range);
          return next;
        }
      }
    } while (!queue.isEmpty());

    return null;
  }

  /**
   * 新生成一个Range数据
   *
   * @param base 初始值
   * @param size 能够使用的大小
   */
  public void offer(long base, long size) {
    queue.add(new Range(base, size));
  }

  private static final class Range {
    /**
     * 记录当前获取的值的索引
     *
     * 使用AtomicLong保证获取下一个值的操作是原子的
     */
    private final AtomicLong counter;

    /**
     * 记录当前批次的初始值
     */
    private final long base;

    /**
     * 记录当前批次最多有多少个值
     */
    private final long size;

    public Range(long base, long size) {
      this.base = base;
      this.size = size;
      this.counter = new AtomicLong(0);
    }

    public Long next() {
      long current = counter.getAndIncrement();
      if (current >= size) {
        return null;
      }

      return base + current;
    }
  }

}
