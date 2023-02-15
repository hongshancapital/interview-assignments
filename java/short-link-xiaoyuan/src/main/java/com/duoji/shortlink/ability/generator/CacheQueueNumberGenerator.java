package com.duoji.shortlink.ability.generator;

import com.duoji.shortlink.ability.model.Counter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 16:18:00
 */
@Slf4j
public class CacheQueueNumberGenerator extends Counter implements NumberGenerator {

    private ArrayBlockingQueue<Long> queue;

    @Override
    public Long ownId() {
        return id;
    }

    @Override
    public Long generateCode() {
        return queue.poll();
    }

    public CacheQueueNumberGenerator(Long id, Long highMaxConfig, Long stepConfig, Integer queueSizeConfig) {

        this.id = id;
        this.highMax = highMaxConfig;
        this.step = stepConfig;
        //todo 判断是否有持久化的high,如果没有都初始化成0
        Long highConfig = 0L;
        this.high = highConfig;
        this.low = highConfig;

        queue = new ArrayBlockingQueue<>(queueSizeConfig);

        //后台线程
        Thread thread = new Thread(() -> {
            while (true) {
                if (this.low >= this.highMax) {
                    log.error("已用尽号码,停止后台线程,id={},low={},high={},step={},highMax={}", id,low, high, step, highMax);
                    return;
                }
                //如果低水位达到高水位,高水位上移
                if (low >= high) {
                    high += step;
                    //高水位不可越过最高水位
                    if (high >= highMax) {
                        high = highMax;
                    }
                    //todo 持久化记录high
                }
                try {
                    //采用阻塞方式放入元素
                    queue.put(low);
                } catch (InterruptedException e) {
                    log.info("[CacheQueueNumberGenerator] daemon thread interrupted", e);
                }
                //低水位移动
                low++;
                //todo 可以加提前预警，比如low>highMax*0.8
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
