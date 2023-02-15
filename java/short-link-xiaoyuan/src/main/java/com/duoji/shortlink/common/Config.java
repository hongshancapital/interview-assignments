package com.duoji.shortlink.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 16:03:00
 */
@Configuration
@Slf4j
public class Config {
    /**
     * 进制
     */
    @Value("${app.config.redix}")
    public Long REDIX;

    /**
     * 总位数
     */
    @Value("${app.config.totalBit}")
    public Integer TOTAL_BIT;

    /**
     * 机器占位
     */
    @Value("${app.config.machineBit}")
    public Integer MACHINE_BIT;

    /**
     * 计数器占用的位数
     */
    @Value("${app.config.counterBit}")
    public Integer COUNTER_BIT;

    /**
     * 编码占用位数
     */
    public Integer CODE_BIT;

    /**
     * 号个数最大值
     */
    public Long CODE_NUM_MIX = 1L;


    /**
     * 计数器个数
     */
    public Long COUNTER_CNT = 1L;

    /**
     * 短链缓存时长(s)
     */
    @Value("${app.config.expireSec}")
    public Long EXPIRE_SEC;


    @PostConstruct
    public void init() {

        check();

        CODE_BIT = TOTAL_BIT - MACHINE_BIT - COUNTER_BIT;

        //计算号池塘的最大值
        for (int i = 0; i < CODE_BIT; i++) {
            CODE_NUM_MIX *= REDIX;
        }

        //计算计数器个数
        for (int i = 0; i < COUNTER_BIT; i++) {
            COUNTER_CNT *= REDIX;
        }

    }

    private void check() {
        if (TOTAL_BIT <= 0 || MACHINE_BIT < 0 || COUNTER_BIT <= 0) {
            log.error("TOTAL_BIT={},MACHINE_BIT={},COUNTER_BIT={} 3参数存在不合理参数,请检查", TOTAL_BIT, MACHINE_BIT, COUNTER_BIT);
            throw new RuntimeException("存在不合理参数");
        }
        if (TOTAL_BIT - MACHINE_BIT - COUNTER_BIT <= 0) {
            log.error("TOTAL_BIT={},MACHINE_BIT={},COUNTER_BIT={} 3参数存在不合理导致TOTAL_BIT - MACHINE_BIT - COUNTER_BIT <= 0,请检查", TOTAL_BIT, MACHINE_BIT, COUNTER_BIT);
            throw new RuntimeException("存在不合理参数");
        }
    }
}
