package com.coderdream.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 配置工具类
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@Configuration
@Slf4j
public class Config {

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
     * 短链缓存时长(s)
     */
    @Value("${app.config.expireSec}")
    public Long EXPIRE_SEC;

}
