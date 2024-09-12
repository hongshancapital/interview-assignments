package com.zoujing.shortlink.generator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleIDGenerator implements IDGenerator {
    /**
     * 发号器编号
     */
    private Long no;

    /**
     * 发号器起始编号
     */
    private Long currentIdNum;

    /**
     * 发号器id最大值
     */
    private Long maxIdNum;

    // 构造函数
    public SimpleIDGenerator(Long no, Long currentIdNum, Long maxIdNum) {
        this.no = no;
        this.currentIdNum = currentIdNum;
        this.maxIdNum = maxIdNum;
    }

    @Override
    public synchronized Long getNextId() {
        if (currentIdNum > maxIdNum) {
            log.error("there is no available id for IDGenerator which no = {}");
            // todo 可添加异步报警机制
            return null;
        }
        Long temp = currentIdNum;
        currentIdNum++;
        // todo 可异步写入磁盘（记录当前发号器发号水位，宕机后重启可从该水位继续发送）
        return temp;
    }

    @Override
    public Long getGeneratorNo() {
        return this.no;
    }
}
