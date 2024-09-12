package com.zoujing.shortlink.generator;

public interface IDGenerator {

    /**
     * 获取自增id
     */
    Long getNextId();

    /**
     * 获取发号器编号
     */
    Long getGeneratorNo();
}
