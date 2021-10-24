package com.interview.assignment.factory;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public class NamedThreadFactory implements ThreadFactory {

  private final AtomicLong id = new AtomicLong(1);

  private String name;

  public NamedThreadFactory(String name) {
    this.name = name;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread thread = new Thread(r);
    thread.setName(name + "-" + id.getAndIncrement());
    thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread t, Throwable e) {
        log.error("exception occurred, thread name: {}", t.getName(), e);
      }
    });

    return thread;
  }
}
