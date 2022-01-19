package com.example.shorturl.service.generator.genstrategy;

import com.example.shorturl.config.GeneratorConfig;
import com.example.shorturl.config.exception.BizException;
import com.example.shorturl.service.dto.GenerateEnum;
import com.example.shorturl.utils.GenerateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author yyp
 * @date 2022/1/16 10:09
 */
@Slf4j
@Service
public class IncrementGeneratorStrategy extends DefaultGenerateStrategy {

    private Integer step;
    private Long start;
    private Long end;

    IncrementGeneratorStrategy(GeneratorConfig generatorConfig) {
        super(generatorConfig);
        this.start = generatorConfig.idx * (GenerateUtils.MAX_VALUE / generatorConfig.shardCount);
        this.end = Math.min((generatorConfig.idx + 1) * (GenerateUtils.MAX_VALUE / generatorConfig.shardCount), GenerateUtils.MAX_VALUE - 1);
        this.step = generatorConfig.step;
        super.queueLen = Math.min(generatorConfig.queueLen, step);

        initQueue();

        generate();
    }

    /**
     * 生成号码
     */
    private void generate() {
        int count = 0;
        for (ArrayBlockingQueue<Long> queue : shortCodeQueue) {
            executorPool.submit(new GenIncrementNumber(start + count++, queue));
        }
    }

    @Override
    public GenerateEnum support() {
        return GenerateEnum.INCREMENT_GENERATOR;
    }

    public class GenIncrementNumber implements Runnable {
        private Long start;
        private ArrayBlockingQueue<Long> queue;

        GenIncrementNumber(Long start, ArrayBlockingQueue<Long> queue) {
            this.start = start;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                Long nextNumber = getNextNumber();
                try {
                    queue.put(nextNumber);
                } catch (InterruptedException e) {
                    log.error("generate shot url interrupted, threadName:{}", Thread.currentThread().getName(), e);
                }
            }
        }

        private Long getNextNumber() {
            if (start > end) {
                throw new BizException("号码器已使用完");
            }
            return start++;
        }
    }
}
