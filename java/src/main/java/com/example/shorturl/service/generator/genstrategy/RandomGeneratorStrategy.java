package com.example.shorturl.service.generator.genstrategy;

import com.example.shorturl.config.GeneratorConfig;
import com.example.shorturl.service.dto.GenerateEnum;
import com.example.shorturl.utils.GenerateUtils;
import com.example.shorturl.utils.ThreadPoolExecutorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yyp
 * @date 2022/1/16 10:09
 */
@Slf4j
@Service
public class RandomGeneratorStrategy extends DefaultGenerateStrategy {
    private Long start;
    private Long end;

    RandomGeneratorStrategy(GeneratorConfig generatorConfig) {
        super(generatorConfig);
        this.queueLen = generatorConfig.queueLen;
        this.queueSize = generatorConfig.queueSize;
        this.start = generatorConfig.idx * (GenerateUtils.MAX_VALUE / generatorConfig.shardCount);
        this.end = Math.min((generatorConfig.idx + 1) * (GenerateUtils.MAX_VALUE / generatorConfig.shardCount), GenerateUtils.MAX_VALUE - 1);

        initQueue();

        generate();
    }

    /**
     * 生成号码
     */
    private void generate() {
        ThreadPoolExecutor executorPool = ThreadPoolExecutorUtils.createExecutorPool();
        for (ArrayBlockingQueue<Long> queue : shortCodeQueue) {
            executorPool.submit(new GenRandomNumber(start, end, queue));
        }
    }

    @Override
    public GenerateEnum support() {
        return GenerateEnum.RANDOM_GENERATOR;
    }

    /**
     * 随机生成号码
     */
    public class GenRandomNumber implements Runnable {
        private Long min;
        private Long max;
        private ArrayBlockingQueue<Long> queue;

        GenRandomNumber(Long min, Long max, ArrayBlockingQueue<Long> queue) {
            this.min = min;
            this.max = max;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                Long nextNumber = getNextNumber();
                //重复概率很小，bitmap加入判重，如果重复了需要重新生成
                try {
                    queue.put(nextNumber);
                } catch (InterruptedException e) {
                    log.error("generate shot url interrupted, threadName:{}", Thread.currentThread().getName(), e);
                }
            }
        }

        private Long getNextNumber() {
            return GenerateUtils.getRandomNumber(min, max);
        }
    }
}
