package org.goofly.shortdomain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.goofly.shortdomain.service.GeneratorService;
import org.goofly.shortdomain.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Value;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class LocalGeneratorServiceImpl implements GeneratorService {

    @Value("${length.limit}")
    private Integer lengthLimit;
    @Value("${queue.list.size:1}")
    private Integer queueListSize;
    @Value("${queue.size:1000}")
    private Integer queueSize;
    private Long maxVal;
    private LongAdder longAdder;

    //通过异步线程将ID提前生成，缓存至队列。 后续可增大线程数量以及队列数量,从而达到扩大吞吐
    private List<BlockingQueue<Long>> queueList;
    private ExecutorService executorService;


    @PostConstruct
    public void init() {
        maxVal = ConvertUtils.getMaxValInBit(lengthLimit);
        longAdder = new LongAdder();

        //初始化缓存队列,异步线程生成code
        executorService = new ThreadPoolExecutor(queueListSize, queueListSize, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        queueList = new ArrayList<>(queueListSize);

        for (int i = 0; i < queueListSize; i++) {
            BlockingQueue<Long> queue = new ArrayBlockingQueue<>(queueSize);
            queueList.add(queue);
            executorService.execute(() -> this.asynGenerateCode(queue));
        }
    }

    @Override
    public Long generateCode() {
        int queueIndex = RandomUtils.nextInt(0, queueListSize);
        //随机选择队列消费
        Long id = queueList.get(queueIndex).poll();
        return id;
    }

    private void asynGenerateCode(BlockingQueue<Long> queue) {
        while (true) {
            if (longAdder.longValue() > maxVal) {
                log.error("generateCode resource exhausted, maxVal:{}", maxVal);
                throw new RuntimeException("generateCode resource exhausted");
            }

            try {
                longAdder.increment();
                //若队列已满,则阻塞
                queue.put(longAdder.longValue());
            } catch (InterruptedException e) {
                log.info("put code into queue failed.", e);
            }
        }
    }
}
