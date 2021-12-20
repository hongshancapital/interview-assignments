package com.duoji.shortlink.ability.generator;

import com.duoji.shortlink.ability.model.Counter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XY
 * @Description
 * @createTime 2021年12月17日 21:21:00
 */
@Slf4j
public class AutoincrementNumberGenerator extends Counter implements NumberGenerator {



    @Override
    public Long ownId() {
        return id;
    }

    @Override
    public synchronized Long generateCode() {
        if (low >= highMax) {
            log.error("已用尽号码,停止服务,low={},high={},step={},highMax={}", low, high, step, highMax);
            return null;
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
        Long temp = low;
        //低水位移动
        low++;
        //todo 可以加提前预警，比如low>highMax*0.8
        return temp;
    }

    public AutoincrementNumberGenerator(Long id, Long highMax, Long step) {
        this.id = id;
        this.highMax = highMax;
        this.step = step;
        //todo 判断是否有持久化的high,如果没有都初始化成0
        Long high = 0L;
        this.high = high;
        this.low = high;

    }

}
