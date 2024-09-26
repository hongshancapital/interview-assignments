package com.example.shorturl.service.generator.genstrategy;

import com.example.shorturl.config.GeneratorConfig;
import com.example.shorturl.config.exception.BizException;
import com.example.shorturl.utils.GenerateUtils;
import com.example.shorturl.utils.ThreadPoolExecutorUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yyp
 * @date 2022/1/16 21:05
 */
@Slf4j
public abstract class DefaultGenerateStrategy implements IGeneratorStrategy {

    protected List<ArrayBlockingQueue<Long>> shortCodeQueue;
    protected Integer queueLen;
    protected Integer queueSize;

    GeneratorConfig generatorConfig;

    ThreadPoolExecutor executorPool = ThreadPoolExecutorUtils.createExecutorPool();

    DefaultGenerateStrategy(GeneratorConfig generatorConfig) {
        this.queueLen = generatorConfig.queueLen;
        this.queueSize = generatorConfig.queueSize;
        this.generatorConfig = generatorConfig;
    }

    /**
     * 初始化队列
     */
    protected void initQueue() {
        int len = this.queueSize;
        shortCodeQueue = new ArrayList<>(len);
        while (len-- > 0) {
            shortCodeQueue.add(new ArrayBlockingQueue<>(this.queueLen));
        }
    }

    @Override
    public String generateCode() {
        Integer index = GenerateUtils.getRandomNumber(queueSize);
        Long number = null;
        try {
            number = shortCodeQueue.get(index).take();
        } catch (InterruptedException e) {
            log.error("generate code interrupted", e);
            throw new BizException("生成短连异常，请重试");
        }
        return GenerateUtils.formatUrlCode(GenerateUtils.longToSixtyTwo(number), generatorConfig.urlLength);
    }
}
