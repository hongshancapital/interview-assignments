package com.domain.urlshortener.core.cache;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 8:31
 */
public class MappingCacheWriteExecutor {

    private static final String THREAD_PREFIX = "cache-write-thread-";

    private ThreadPoolExecutor threadPoolExecutor;

    public MappingCacheWriteExecutor(Integer queueSize) {
        threadPoolExecutor =  new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize),
                new CacheWriteThreadFactory(THREAD_PREFIX,  true),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public void submit(MappingCacheWriteTask writeTask) {
        threadPoolExecutor.submit(writeTask);
    }

    private class CacheWriteThreadFactory implements ThreadFactory {

        private AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final boolean daemon;

        public CacheWriteThreadFactory(String namePrefix, boolean daemon) {
            this.namePrefix = namePrefix;
            this.daemon = daemon;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(Thread.currentThread().getThreadGroup(), r, this.namePrefix + this.threadNumber.getAndIncrement());
            t.setDaemon(this.daemon);
            return t;
        }

    }

}
